package com.telchina.image.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;

import com.orhanobut.logger.Logger;
import com.telchina.image.activity.ImagePicker;
import com.telchina.image.bean.PhotoDirectory;
import com.telchina.image.bean.PhotoItem;

public class PhotoDirGetTask extends
        AsyncTask<String[], Integer, List<PhotoDirectory>> {

    private Context mContext;

    private PhotoDirCallback mCallback;

    //根目录
    private String mRootDir = null;

    public PhotoDirGetTask(Context context, PhotoDirCallback callback) {
        mContext = context;
        mCallback = callback;
        mRootDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();

    @Override
    protected List<PhotoDirectory> doInBackground(String[]... params) {
        List<PhotoDirectory> mImageFloder = new ArrayList<PhotoDirectory>();

        ContentResolver mContentResolver = mContext.getContentResolver();

        String key = "";
        String[] values = new String[ImagePicker.PHOTO_EXTENTION.length];
        for (int i = 0; i < ImagePicker.PHOTO_EXTENTION.length; i++) {
            key += Media.MIME_TYPE + "=? or ";
            values[i] = "image/" + ImagePicker.PHOTO_EXTENTION[i];
        }
        key = key.substring(0, key.length() - 3);

        // 只查询指定扩展名的图片
        // 只查询特定的column
        String[] projection = {Media._ID, Media.DATA,
                Media.BUCKET_DISPLAY_NAME};
        Cursor mCursor = mContentResolver.query(Media.EXTERNAL_CONTENT_URI, projection, key,
                values, Media.DATE_MODIFIED + " DESC");

        while (mCursor.moveToNext()) {
            // 图片所在的文件夹名称
            String parentDirName = mCursor.getString(mCursor
                    .getColumnIndex(Media.BUCKET_DISPLAY_NAME));
            // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
            if (mDirPaths.contains(parentDirName)) {
                continue;
            } else {
                // 获取该路径下的第一张图片
                String firstImgPath = mCursor.getString(mCursor
                        .getColumnIndex(Media.DATA));
                File parentFile = new File(firstImgPath).getParentFile();
                if (parentFile == null)// 不显示存储卡根目录下的图片
                    continue;
                String parentDir = parentFile.getAbsolutePath();
                if (parentDir.equals(mRootDir))// 不显示存储卡根目录下的图片
                    continue;
                // 初始化PhotoDirectory
                PhotoDirectory directory = new PhotoDirectory();
                directory.setName(parentDirName);
                directory.setDir(parentDir);
                // 查询第一张图片原图和缩略图
                PhotoItem photo = new PhotoItem();
                photo.setmPath("file://" + firstImgPath);

                directory.setFirstPhoto(photo);
                Logger.d(parentFile.getAbsolutePath());
                // 该路径下的图片数量
                int picCount = parentFile.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        for (String ext : ImagePicker.PHOTO_EXTENTION) {
                            if (filename.endsWith("." + ext))
                                return true;
                        }
                        return false;
                    }
                }).length;
                directory.setCount(picCount);

                mDirPaths.add(parentDirName);
                mImageFloder.add(directory);
            }

        }
        mCursor.close();

        //按照目录中的图片数量降序排序
        if (!mImageFloder.isEmpty()) {
            Collections.sort(mImageFloder, new Comparator<PhotoDirectory>() {

                @Override
                public int compare(PhotoDirectory p1, PhotoDirectory p2) {
                    Integer count1 = p1.getCount();
                    Integer count2 = p2.getCount();
                    return count2.compareTo(count1);
                }
            });
        }

        // 扫描完成，辅助的HashSet也就可以释放内存了
        mDirPaths = null;
        System.gc();
        return mImageFloder;
    }

    @Override
    protected void onPostExecute(List<PhotoDirectory> result) {
        if (mCallback != null)
            mCallback.onFinish(result);
        super.onPostExecute(result);
    }

    /**
     * 根据原图ID获取缩略图地址
     *
     * @param imageId
     * @return
     * @author GISirFive
     */
    private String getThumbnail(String imageId) {
        String thumbPath = null;
        ContentResolver mContentResolver = mContext.getContentResolver();
        String[] projThumb = {Thumbnails.DATA};
        Cursor cursor = mContentResolver.query(
                Thumbnails.EXTERNAL_CONTENT_URI, projThumb,
                Thumbnails.IMAGE_ID + "=" + imageId, null, null);
        while (cursor.moveToNext()) {
            thumbPath = cursor.getString(cursor
                    .getColumnIndex(Thumbnails.DATA));

            if (thumbPath != null && thumbPath.length() != 0) {
                //判断缩略图存不存在
                File file = new File(thumbPath);
                if (!file.exists())
                    thumbPath = null;
            }
        }
        cursor.close();
        if (thumbPath != null)
            thumbPath = "file://" + thumbPath;
        return thumbPath;
    }

    // 创建一个回调接口，用于在子线程执行时调回主线程，执行自定义操作
    public interface PhotoDirCallback {
        void onFinish(List<PhotoDirectory> result);
    }
}
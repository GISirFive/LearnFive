package com.telchina.image.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore.Images.Media;
import android.util.Log;

import com.telchina.image.activity.ImagePicker;
import com.telchina.image.bean.PhotoDirectory;
import com.telchina.image.bean.PhotoItem;


public class PhotoGetTask extends
        AsyncTask<PhotoDirectory, Integer, List<PhotoItem>> {

    private Context mContext;
    private PhotoCallback mCallback;

    public PhotoGetTask(Context context, PhotoCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    @Override
    protected List<PhotoItem> doInBackground(PhotoDirectory... params) {
        PhotoDirectory directory = params[0];
        List<PhotoItem> photoList = new ArrayList<PhotoItem>();

        ContentResolver mContentResolver = mContext.getContentResolver();

        // 查询条件和扩展名
        String key = "";
        String[] values = new String[ImagePicker.PHOTO_EXTENTION.length + 1];
        for (int i = 0; i < ImagePicker.PHOTO_EXTENTION.length; i++) {
            key += Media.MIME_TYPE + "=? or ";
            values[i] = "image/" + ImagePicker.PHOTO_EXTENTION[i];
        }
        key = key.substring(0, key.length() - 3);
        key = "(" + key + ") and " + Media.BUCKET_DISPLAY_NAME + "=?";
        values[values.length - 1] = directory.getName();

        key = Media.BUCKET_DISPLAY_NAME + "=?";
        values = new String[]{directory.getName()};

        // 只查询特定的column
        String[] projection = {Media._ID, Media.TITLE, Media.DATA, Media.DATE_MODIFIED,
                Media.BUCKET_DISPLAY_NAME};
        Cursor mCursor = mContentResolver.query(Media.EXTERNAL_CONTENT_URI,
                projection, key, values, Media.DATE_MODIFIED + " DESC");
        while (mCursor.moveToNext()) {
            PhotoItem photo = new PhotoItem();
            //名称
            String name = mCursor
                    .getString(mCursor.getColumnIndex(Media.TITLE));
            //路径
            String path = mCursor.getString(mCursor.getColumnIndex(Media.DATA));
            long modifyDate = mCursor.getLong(mCursor
                    .getColumnIndex(Media.DATE_MODIFIED));
            //日期
            Date date = new Date(modifyDate * 1000);

            photo.setmName(name);
            photo.setmPath("file://" + path);
            photo.setmMonifyDate(date);
            //缩略图
//			String imageId = mCursor.getString(mCursor.getColumnIndex(Media._ID));
//			String thumbPath = getThumbnail(imageId);
//			photo.setmThunmnailPath(thumbPath);

            photoList.add(photo);
        }

        return photoList;
    }

    @Override
    protected void onPostExecute(List<PhotoItem> result) {
        if (mCallback != null)
            mCallback.onFinish(result);
        super.onPostExecute(result);
    }

    // 创建一个回调接口，用于在子线程执行时调回主线程，执行自定义操作
    public interface PhotoCallback {
        void onFinish(List<PhotoItem> result);
    }
}
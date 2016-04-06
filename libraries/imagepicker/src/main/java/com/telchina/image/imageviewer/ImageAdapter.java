package com.telchina.image.imageviewer;

import java.util.ArrayList;
import java.util.List;

import com.telchina.image.R;
import com.telchina.image.bean.PhotoItem;
import com.telchina.pub.application.BaseApp;
import com.widget.photoview.PhotoView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class ImageAdapter extends PagerAdapter {

    private List<PhotoItem> mCheckedList = null;

    private LayoutInflater mInflater;

    public ImageAdapter(Context context, List<PhotoItem> list) {
        mInflater = LayoutInflater.from(context);
        mCheckedList = list;
        if (mCheckedList == null)
            mCheckedList = new ArrayList<PhotoItem>();
    }

    public int getCount() {
        return mCheckedList.size();
    }

    /**
     * 获取图片的封装类
     *
     * @param position
     * @return
     * @author GISirFive
     */
    public PhotoItem getItem(int position) {
        return mCheckedList.get(position);
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View root = mInflater.inflate(R.layout.imageviewer_item, null);
        PhotoView photoView = (PhotoView) root.findViewById(R.id.photoview);
        String url = getItem(position).getmPath();
        if (url != null) {
//            BaseApp.getImageLoader().displayThumbMini(url, photoView);
            BaseApp.ImageLoader.displayMini(url, photoView);
        } else {
//            BaseApp.display("drawable://" + R.mipmap.url_load_image_failed, photoView);
            BaseApp.ImageLoader.display("drawable://" + R.drawable.url_load_image_failed, photoView);
        }
        container.addView(root, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);

        return root;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//		PhotoView tempPhotoView = (PhotoView) object;
//		Bitmap photoBitmap = tempPhotoView.getDrawingCache();
//		photoBitmap.recycle();
//		photoBitmap = null;
        container.removeView((View) object);
//		System.gc();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public ArrayList<PhotoItem> getCheckedList() {
        ArrayList<PhotoItem> list = new ArrayList<PhotoItem>();
        for (PhotoItem photo : mCheckedList) {
            if (photo.ismChecked())
                list.add(photo);
        }
        return list;
    }
}

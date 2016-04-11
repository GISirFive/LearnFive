package com.telchina.image.imagepicker;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.telchina.image.R;
import com.telchina.image.bean.PhotoDirectory;
import com.telchina.image.bean.PhotoItem;
import com.telchina.pub.application.BaseApp;

/**
 * 图片目录
 *
 * @author GISirFive
 * @since 2016-1-20 上午11:21:14
 */
public class DirectoryAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<PhotoDirectory> mList;

    public DirectoryAdapter(Context context, List<PhotoDirectory> mDatas) {
        mInflater = LayoutInflater.from(context);
        mList = new ArrayList<PhotoDirectory>();
        if (mDatas != null)
            mList.addAll(mDatas);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public PhotoDirectory getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.imagepicker_drawerlist_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PhotoDirectory directory = getItem(position);
        holder.title.setText(directory.getName());
        PhotoItem photo = directory.getFirstPhoto();
        Glide.with(mInflater.getContext()).load(photo.getmPath()).into(holder.thumbnail);
//        BaseApp.ImageLoader.displayMini(photo.getmPath(), holder.thumbnail);
//        BaseApp.getImageLoader().displayThumbMicro(photo.getmPath(), holder.thumbnail);
        holder.count.setText(directory.getCount() + "张");
        return convertView;
    }

    /**
     * 重置数据
     *
     * @param list
     * @author GISirFive
     */
    public void resetData(List<PhotoDirectory> list) {
        mList.clear();
        if (list != null)
            mList.addAll(list);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView count;

        public ViewHolder(View root) {
            thumbnail = (ImageView) root.findViewById(R.id.imageview_thumbnail);
            title = (TextView) root.findViewById(R.id.textview_title);
            count = (TextView) root.findViewById(R.id.textview_count);
        }
    }

}

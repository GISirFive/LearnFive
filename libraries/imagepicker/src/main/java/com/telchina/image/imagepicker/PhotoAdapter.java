package com.telchina.image.imagepicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.telchina.image.R;
import com.telchina.image.bean.PhotoItem;
import com.telchina.pub.application.BaseApp;
import com.telchina.pub.image.LoaderType;
import com.utils.DateUtils;
import com.widget.stickygridheaders.StickyGridHeadersSimpleAdapter;

public class PhotoAdapter extends BaseAdapter implements
        StickyGridHeadersSimpleAdapter {

    /**
     * 最多可以选择几张
     */
    private int maxSelectNum = 5;
    /**
     * 被选中的图片
     */
    private List<PhotoItem> mCheckedList = new ArrayList<PhotoItem>();

    private LayoutInflater mInflater;
    private List<PhotoItem> mList;

    public PhotoAdapter(Context context, List<PhotoItem> mDatas) {
        mInflater = LayoutInflater.from(context);
        mList = new ArrayList<PhotoItem>();
        if (mDatas != null)
            mList.addAll(mDatas);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public PhotoItem getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.imagepicker_grid_item,
                    parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PhotoItem photo = getItem(position);

        if (mCheckedList.contains(photo)) {
            holder.checked.setVisibility(View.VISIBLE);
        } else {
            holder.checked.setVisibility(View.GONE);
        }
//        BaseApp.getImageLoader().displayThumbMicro(photo.getmPath(), holder.thumbnail);
//        BaseApp.ImageLoader.display(photo.getmPath(), holder.thumbnail, LoaderType._MICRO);
        Glide.with(mInflater.getContext()).load(photo.getmPath()).into(holder.thumbnail);
        return convertView;
    }

    @Override
    public String getHeaderId(int position) {
        PhotoItem item = getItem(position);
        Date date = item.getmMonifyDate();
        if (date == null)
            return "0000-00-00";
        return DateUtils.getDate(item.getmMonifyDate());
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.imagepicker_grid_header,
                    parent, false);
            holder = new HeaderViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        PhotoItem item = getItem(position);
        String date = DateUtils.getDate(item.getmMonifyDate());
        holder.date.setText(date);
        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        // return super.areAllItemsEnabled();
        return false;
    }

    /**
     * 重置数据
     *
     * @param list
     * @author GISirFive
     */
    public void resetData(List<PhotoItem> list) {
        mList.clear();
        if (list != null)
            mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearChecked() {
        mCheckedList.clear();
        notifyDataSetChanged();
    }

    public ArrayList<PhotoItem> getCheckedList() {
        ArrayList<PhotoItem> list = new ArrayList<PhotoItem>();
        list.addAll(mCheckedList);
        return list;
    }

    public void setCheckedList(List<PhotoItem> list) {
        if (list == null)
            list = new ArrayList<PhotoItem>();
        mCheckedList = list;
        notifyDataSetChanged();
    }

    /**
     * 最多可以选择几张
     *
     * @param max
     */
    public void setMaxSelectNum(int max) {
        this.maxSelectNum = max;
    }

    /**
     * 最多可以选择几张
     */
    public int getMaxSelectNum() {
        return this.maxSelectNum;
    }

    /**
     * 改变Item的显示状态
     *
     * @param position
     * @param v
     * @return 返回被选中的item总数
     */
    public int toggleItem(int position, View v) {
        if (position >= mList.size())
            return mCheckedList.size();
        View mChecker = v.findViewById(R.id.imageview_check);
        if (mCheckedList.contains(getItem(position))) {
            mCheckedList.remove(getItem(position));
            mChecker.setVisibility(View.GONE);
        } else {
            if (mCheckedList.size() >= maxSelectNum) {
                Toast.makeText(mInflater.getContext(),
                        "一次最多可上传" + maxSelectNum + "张图片", Toast.LENGTH_SHORT)
                        .show();
            } else {
                mCheckedList.add(getItem(position));
                mChecker.setVisibility(View.VISIBLE);
            }
        }
        return mCheckedList.size();
    }

    private class ViewHolder {
        ImageView thumbnail;
        ImageView checked;

        public ViewHolder(View root) {
            thumbnail = (ImageView) root.findViewById(R.id.imageview_thumbnail);
            checked = (ImageView) root.findViewById(R.id.imageview_check);
        }
    }

    private class HeaderViewHolder {
        public TextView date;

        public HeaderViewHolder(View root) {
            date = (TextView) root.findViewById(R.id.textview_date);
        }
    }
}

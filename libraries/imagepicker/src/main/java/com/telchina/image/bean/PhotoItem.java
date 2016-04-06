package com.telchina.image.bean;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图片
 *
 * @author GISirFive
 * @since 2015-12-29 上午10:30:08
 */
public class PhotoItem implements Parcelable {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PhotoItem))
            return false;
        PhotoItem p = (PhotoItem) o;

        if (p.getmPath().equals(this.getmPath()))
            return true;

        return super.equals(o);
    }

    public PhotoItem() {
    }

    public PhotoItem(String mName, String mPath) {
        super();
        this.mName = mName;
        this.mPath = mPath;
    }

    /**
     * 图片名称
     */
    private String mName;

    /**
     * 图片路径
     */
    private String mPath;

    /**
     * 缩略图路径
     */
    private String mThunmnailPath;

    /**
     * 直接包含图片的文件夹
     */
    private String mBucket;

    /**
     * 图片修改时间
     */
    private Date mMonifyDate;

    /**
     * 是否被选中
     */
    private boolean mChecked = false;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public String getmThunmnailPath() {
        return mThunmnailPath;
    }

    public void setmThunmnailPath(String mThunmnailPath) {
        this.mThunmnailPath = mThunmnailPath;
    }

    public String getmBucket() {
        return mBucket;
    }

    public void setmBucket(String mBucket) {
        this.mBucket = mBucket;
    }

    public boolean ismChecked() {
        return mChecked;
    }

    public void setmChecked(boolean mChecked) {
        this.mChecked = mChecked;
    }

    public Date getmMonifyDate() {
        return mMonifyDate;
    }

    public void setmMonifyDate(Date mMonifyDate) {
        this.mMonifyDate = mMonifyDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mPath);
        dest.writeString(mThunmnailPath);
        dest.writeInt(mChecked ? 1 : 0);
        dest.writeString(mBucket);
    }

    public static final Creator<PhotoItem> CREATOR = new Creator<PhotoItem>() {
        public PhotoItem createFromParcel(Parcel in) {
            return new PhotoItem(in);
        }

        public PhotoItem[] newArray(int size) {
            return new PhotoItem[size];
        }
    };

    public PhotoItem(Parcel in) {
        mName = in.readString();
        mPath = in.readString();
        mThunmnailPath = in.readString();
        mChecked = (in.readInt() == 1);
        mBucket = in.readString();
    }

}

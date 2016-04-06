package com.telchina.image.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.telchina.image.R;
import com.telchina.image.bean.PhotoItem;
import com.telchina.image.imageviewer.ImageAdapter;
import com.telchina.pub.construction.AbsBaseActivity;
import com.telchina.pub.construction.base.ToolbarParam;
import com.widget.photoview.HackyViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ImageViewer extends AbsBaseActivity implements OnClickListener,
        OnPageChangeListener {

    /**
     * 需要首先展示的某一张图片在图片集合中的索引<b>Integer</b>
     */
    public static final String FLAG_INPUT_INDEX = "index";

    /**
     * 控制图片是否可以选择/取消选择 <br>
     * <b>true(默认)</b>-显示底部选择栏<br>
     * <b>false</b>-隐藏底部选择栏
     */
    public static final String FLAG_CHECK_IS_USE = "flagCheckIsUse";

    private HackyViewPager mViewPager;
    private CheckBox mCheckBox;
    /**
     * 图片总数
     */
    private int mSize;
    private Button mOKButton;
    private ImageAdapter mAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageviewer);

        init();

        loadIntent();
    }

    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public boolean onToolbarConfiguration(Toolbar toolbar, ToolbarParam param) {
        boolean checkable = getIntent().getBooleanExtra(FLAG_CHECK_IS_USE, true);
        if (!checkable) {//预览图片模式，标题栏透明
            param.overlay = true;
            param.colorResId = android.R.color.transparent;
            toolbar.setTitle("");
        } else {//选择图片模式
            toolbar.setTitle("预览");
        }
        return true;
    }

    @Override
    public boolean translucent() {
        boolean checkable = getIntent().getBooleanExtra(FLAG_CHECK_IS_USE, true);
        return checkable;
    }

    @Override
    public void onClick(View v) {
        if (v == mCheckBox) {
            int position = mViewPager.getCurrentItem();
            boolean isChecked = mAdapter.getItem(position).ismChecked();
            mAdapter.getItem(position).setmChecked(!isChecked);
            setSelectNum(!isChecked);
        }
        if (v == mOKButton) {
            ArrayList<PhotoItem> checkedList = mAdapter.getCheckedList();
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(ImagePicker.FLAG_IN_OUT_PHOTOS, checkedList);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int index) {
        if (mAdapter.getItem(index).ismChecked()) {
            mCheckBox.setChecked(true);
        } else {
            mCheckBox.setChecked(false);
        }
    }

    private void init() {
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        mViewPager.addOnPageChangeListener(this);

        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
        mCheckBox.setOnClickListener(this);

        mOKButton = (Button) findViewById(R.id.button_ok);
        mOKButton.setOnClickListener(this);
    }

    private void loadIntent() {
        // 判断是否需要显示底部选择栏
        boolean checkUse = getIntent().getBooleanExtra(FLAG_CHECK_IS_USE, true);
        if (!checkUse) {
            RelativeLayout checkLayout = (RelativeLayout) findViewById(R.id.layout_bottombar);
            checkLayout.setVisibility(View.GONE);
        }

        List<PhotoItem> checkedList = getIntent().getParcelableArrayListExtra(
                ImagePicker.FLAG_IN_OUT_PHOTOS);

        if (checkedList != null) {
            for (PhotoItem photo : checkedList) {
                photo.setmChecked(true);
            }

            mAdapter = new ImageAdapter(this, checkedList);
            mViewPager.setAdapter(mAdapter);

            mSize = checkedList.size();
            mSize++;
            setSelectNum(false);
        }

        int index = getIntent().getIntExtra(FLAG_INPUT_INDEX, 0);
        if (index < mAdapter.getCount()) {
            mViewPager.setCurrentItem(index);
        }
    }

    /**
     * 设置确定按钮的中显示的选中照片的数量
     *
     * @param change<br> true-加1<br>
     *                   false-减1
     * @author GISirFive
     */
    private void setSelectNum(boolean change) {
        mSize = mSize + (change ? 1 : -1);
        if (mSize > 0) {
            mOKButton.setText("确定(" + mSize + ")");
        } else {
            mOKButton.setText("确定");
        }
    }
}
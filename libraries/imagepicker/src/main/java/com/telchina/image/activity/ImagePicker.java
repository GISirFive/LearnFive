package com.telchina.image.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialog.MaterialDialog;
import com.telchina.image.R;
import com.telchina.image.bean.PhotoDirectory;
import com.telchina.image.bean.PhotoItem;
import com.telchina.image.imagepicker.DirectoryAdapter;
import com.telchina.image.imagepicker.PhotoAdapter;
import com.telchina.image.utils.PhotoDirGetTask;
import com.telchina.image.utils.PhotoGetTask;
import com.telchina.pub.application.BaseApp;
import com.telchina.pub.construction.AbsBaseActivity;
import com.telchina.pub.construction.base.ToolbarParam;

public class ImagePicker extends AbsBaseActivity implements OnClickListener,
        OnItemClickListener {

    /**
     * 最多可以选择几张
     */
    public static final String FLAG_INPUT_SELECTNUM = "maxNum";
    /**
     * 传入的图片/传出的图片<b>ArrayList< PhotoItem ></b>
     */
    public static final String FLAG_IN_OUT_PHOTOS = "photos";

    /**
     * 文件扩展名
     */
    public static String[] PHOTO_EXTENTION = null;
    /**
     * 相机回调
     */
    private static final int FLAG_PHOTO = 100;
    /**
     * 预览回调
     */
    private static final int FLAG_IMAGEVIEWER = 101;

    private DrawerLayout mDrawerLayout;
    private GridView mGridView;
    private ListView mListView;
    private Button mBtnPreview, mBtnOk;

    private ActionBarDrawerToggle mDrawerToggle;
    // 文件夹适配器
    private DirectoryAdapter mFolderAdapter;
    // 照片适配器
    private PhotoAdapter mPhotoAdapter;
    // 头像地址
    // private Uri imageUri;
    // 相机拍的照片的路径
    private String mCameraFilePath = null;

    private MaterialDialog.Builder mDialogBuilder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepicker);

        PHOTO_EXTENTION = getResources().getStringArray(
                R.array.app_photo_extention);

        init();
        loadIntent();

        mDialogBuilder = new MaterialDialog.Builder(this)
                .content("正在加载...")
                .progress(true, 0)
                .progressIndeterminateStyle(false);
        final MaterialDialog dialog = mDialogBuilder.show();

        new PhotoDirGetTask(this, new PhotoDirGetTask.PhotoDirCallback() {

            @Override
            public void onFinish(List<PhotoDirectory> result) {
                mFolderAdapter = new DirectoryAdapter(ImagePicker.this, result);
                mListView.setAdapter(mFolderAdapter);
                mDrawerLayout.openDrawer(mListView);
                if(dialog.isShowing())
                    dialog.dismiss();
            }
        }).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.imagepicker, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mListView)) {
                mDrawerLayout.closeDrawer(mListView);
            } else {
                mDrawerLayout.openDrawer(mListView);
            }
        } else if (item.getItemId() == R.id.action_camera) {// 打开相机

            if (mPhotoAdapter.getCheckedList().size() >= mPhotoAdapter
                    .getMaxSelectNum()) {
                Toast.makeText(ImagePicker.this, "一次最多上传" + mPhotoAdapter.getMaxSelectNum() +
                        "张图片", Toast.LENGTH_SHORT).show();
            } else {
                ImageClipper.createMkdir();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mCameraFilePath = BaseApp.getDiskCachePath()
                        + System.currentTimeMillis() + ".jpg";
                Uri uri = Uri.fromFile(new File(mCameraFilePath));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, FLAG_PHOTO);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onToolbarConfiguration(android.support.v7.widget.Toolbar toolbar, ToolbarParam param) {
//        return super.onToolbarConfiguration(toolbar, param);
        return true;
    }

    @Override
    public void onClick(View v) {
        ArrayList<PhotoItem> checkedList = mPhotoAdapter.getCheckedList();
        if (checkedList.size() == 0) {
            Toast.makeText(ImagePicker.this, "至少选择一张图片", Toast.LENGTH_SHORT).show();
            return;
        }
        if (v == mBtnOk) {// 确定
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(FLAG_IN_OUT_PHOTOS, checkedList);
            setResult(RESULT_OK, intent);
            finish();
        } else if (v == mBtnPreview) {// 预览
            Intent intent = new Intent(ImagePicker.this, ImageViewer.class);
            intent.putParcelableArrayListExtra(FLAG_IN_OUT_PHOTOS, checkedList);
            startActivityForResult(intent, FLAG_IMAGEVIEWER);
        }
    }

    private void init() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        mListView = (ListView) findViewById(R.id.listview_content);
        mGridView = (GridView) findViewById(R.id.gridview_content);
        mBtnPreview = (Button) findViewById(R.id.button_preview);
        mBtnPreview.setOnClickListener(this);
        mBtnOk = (Button) findViewById(R.id.button_ok);
        mBtnOk.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.app_name, R.string.app_name);

        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mPhotoAdapter = new PhotoAdapter(this, null);
        mFolderAdapter = new DirectoryAdapter(this, null);
        mGridView.setAdapter(mPhotoAdapter);

        mListView.setOnItemClickListener(this);
        mGridView.setOnItemClickListener(this);
//		mListView.setOnScrollListener(new PauseOnScrollListener(
//				BaseApp.ImageLoader, false, false));
//		mGridView.setOnScrollListener(new PauseOnScrollListener(
//				BaseApp.ImageLoader, false, false));
    }

    /**
     * 设置确定按钮的中显示的选中照片的数量
     *
     * @param size
     * @author GISirFive
     */
    private void setmBtnOk(int size) {
        if (size < 1) {
            mBtnOk.setEnabled(false);
            mBtnOk.setText("确定");
        } else {
            mBtnOk.setEnabled(true);
            mBtnOk.setText("确定(" + size + ")");
        }
    }

    /**
     * 读取从请求方地方传过来的数据
     */
    private void loadIntent() {
        // 同步被选中的图片
        ArrayList<PhotoItem> checkedList = getIntent()
                .getParcelableArrayListExtra(ImagePicker.FLAG_IN_OUT_PHOTOS);
        if (checkedList != null) {
            if (checkedList.size() > 0) {
                mBtnOk.setEnabled(true);
                mBtnOk.setText("确定(" + checkedList.size() + ")");
            } else {
                mBtnOk.setEnabled(false);
                mBtnOk.setText("确定");
            }
            mPhotoAdapter.setCheckedList(checkedList);
        }

        // 最多选几张
        int max = getIntent().getIntExtra(FLAG_INPUT_SELECTNUM, -1);
        if (max != -1) {
            mPhotoAdapter.setMaxSelectNum(max);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FLAG_PHOTO:
                if (resultCode == RESULT_OK) {
                    ArrayList<PhotoItem> checkedList = mPhotoAdapter
                            .getCheckedList();
                    PhotoItem photo = new PhotoItem();
                    photo.setmPath("file://" + mCameraFilePath);
                    checkedList.add(photo);

                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra(FLAG_IN_OUT_PHOTOS,
                            checkedList);
                    setResult(RESULT_OK, intent);
                    finish();
                }

                break;
            case FLAG_IMAGEVIEWER:
                if (resultCode == RESULT_OK) {
                    List<PhotoItem> checkedList = data
                            .getParcelableArrayListExtra(FLAG_IN_OUT_PHOTOS);
                    mPhotoAdapter.setCheckedList(checkedList);
                    setmBtnOk(checkedList.size());
                }
                break;

            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (parent == mGridView) {
            int size = mPhotoAdapter.toggleItem(position, view);
            setmBtnOk(size);
        }
        if (parent == mListView) {
            mDrawerLayout.closeDrawer(mListView);
            final MaterialDialog dialog = mDialogBuilder.show();
            PhotoDirectory directory = mFolderAdapter.getItem(position);
            new PhotoGetTask(ImagePicker.this, new PhotoGetTask.PhotoCallback() {

                @Override
                public void onFinish(List<PhotoItem> result) {
                    mPhotoAdapter.resetData(result);
                    if(dialog.isShowing())
                        dialog.dismiss();
                }
            }).execute(directory);

        }
    }

    @Override
    protected void onDestroy() {
        setContentView(R.layout.pub_view_null);
        super.onDestroy();
    }
}

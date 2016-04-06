package com.telchina.image.activity;

import java.io.File;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.telchina.image.R;
import com.telchina.image.imagecliper.ClipImageLayout;
import com.telchina.image.utils.ImageTools;
import com.telchina.pub.application.BaseApp;
import com.telchina.pub.construction.AbsToolbarActivity;
import com.telchina.pub.construction.base.ToolbarParam;


public class ImageClipper extends AbsToolbarActivity {
    private ClipImageLayout mClipImageLayout;
    private String path;
    private ProgressDialog loadingDialog;
    /**
     * 裁剪以后的头像名字
     */
    private static final String IMAGE_PORTRAIT_NAME = "image_jmp0x122023x.jpg";
    /**
     * 裁剪以后的头像名字,连续多次修改头像将产生两张图片，循环调用
     */
    private static final String IMAGE_PORTRAIT_NAME2 = "image_jmp0x122020x.jpg";
    /**
     * 判断是否多次修改头像
     */
    private static boolean IMAGE_PORTRAIT_S = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageclipper);
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setTitle("请稍后...");
        path = getIntent().getStringExtra("path");
        Uri uri = Uri.parse(path);
        File file = new File(uri.getPath());
        if (!file.exists())
            finish();
        path = file.getAbsolutePath();
        Bitmap bitmap = ImageTools.convertToBitmap(path, 600, 600);
        if (bitmap == null) {
            Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
        mClipImageLayout.setBitmap(bitmap);
        ((Button) findViewById(R.id.id_action_clip))
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        loadingDialog.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap bitmap = mClipImageLayout.clip();
                                String path;
                                createMkdir();
                                if (IMAGE_PORTRAIT_S) {
                                    IMAGE_PORTRAIT_S = false;
                                    path = BaseApp.getDiskCachePath() + IMAGE_PORTRAIT_NAME;
                                } else {
                                    IMAGE_PORTRAIT_S = true;
                                    path = BaseApp.getDiskCachePath() + IMAGE_PORTRAIT_NAME2;
                                }
                                ImageTools.savePhotoToSDCard(bitmap, path);
                                loadingDialog.dismiss();
                                Intent intent = new Intent();
                                intent.putExtra("path", path);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }).start();
                    }
                });
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public boolean onToolbarConfiguration(Toolbar toolbar, ToolbarParam param) {
        getLayoutInflater().inflate(R.layout.pub_toobar_1, toolbar);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.textview_title);
        tvTitle.setText("头像裁剪");
        return super.onToolbarConfiguration(toolbar, param);
    }

    public static void createMkdir() {
        File folder = new File(BaseApp.getDiskCachePath());
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}

package com.telchina.init.application;

import android.app.Application;

import com.github.yoojia.anyversion.AnyVersion;
import com.github.yoojia.anyversion.Version;
import com.github.yoojia.anyversion.VersionParser;
import com.loopj.android.http.PersistentCookieStore;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.telchina.init.http.IRequestControllerImp;
import com.telchina.init.image.ImageLoader;
import com.telchina.pub.utils.Config;
import com.xiaomi.mistatistic.sdk.MiStatInterface;
import com.xiaomi.mistatistic.sdk.URLStatsRecorder;
import com.xiaomi.mistatistic.sdk.controller.HttpEventFilter;
import com.xiaomi.mistatistic.sdk.data.HttpEvent;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.telchina.pub.application.IAppInitController;
import com.telchina.pub.image.IImageLoader;
import com.telchina.pub.http.IRequestController;
import com.utils.AESUtils;

/**
 * 初始化App接口的实现
 *
 * @author GISirFive
 * @since 2016-1-12 下午2:44:25
 */
public class AppInitControllerImp implements IAppInitController {

    private Application mApplication;

    /**
     * 数据库版本
     */
    private int DB_VERSION;

    public AppInitControllerImp(Application application) {
        mApplication = application;


        try {
            DB_VERSION = Config.DB_VERSION;
        } catch (Exception e) {

        }
    }

    @Override
    public void init() {
        /*initLog();
        // 安全性
        initSecurity();
        // 版本更新
        initUpdateVersion();
        // 加载图片
        getImageLoader();
        // 短信验证
        initSMS();
        // 社会化分享
        initSocialization();
        // 创建数据库
        initDatabase();
        // 统计信息
        initStatistics();*/

    }

    @Override
    public void initLog() {
        Logger.init(mApplication.getResources().getString(com.telchina.pub.R.string.app_name))
                .methodCount(2)
                .hideThreadInfo()
                .logLevel(LogLevel.FULL)
                .methodOffset(2);
    }

    @Override
    public void initSecurity() {
        AESUtils.init("jianbangjituansb");
    }

    @Override
    public IRequestController getHttpClient() {
        return IRequestControllerImp.getInstance();
    }

    @Override
    public void initUpdateVersion() {
        AnyVersion.init(mApplication, new VersionParser() {
            @Override
            public Version onParse(String response) {
                final JSONTokener tokener = new JSONTokener(response);
                try {
                    JSONObject json = (JSONObject) tokener.nextValue();
                    json = json.getJSONObject("data");
                    Version version = new Version(
                            json.getString("versionname"), json
                            .getString("versionnote"), json
                            .getString("versionurl"), json
                            .getInt("versioncode"));
                    return version;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    @Override
    public IImageLoader getImageLoader() {
//        return ImageLoader.init(mApplication);
        return new com.telchina.init.glide.IImageLoaderImp(mApplication);
    }

    @Override
    public void initCookieStore() {
        PersistentCookieStore cookieStore = new PersistentCookieStore(
                mApplication);
        IRequestControllerImp.getInstance().getBase().setCookieStore(cookieStore);
    }

    @Override
    public void initDatabase() {
    }

    @Override
    public void initSMS() {

    }

    @Override
    public void initSocialization() {

    }

    @Override
    public void initStatistics() {
        // regular stats.
        MiStatInterface.initialize(mApplication, "2882303761517427645",
                "5951742762645", null);
        MiStatInterface.setUploadPolicy(
                MiStatInterface.UPLOAD_POLICY_WIFI_ONLY, 0);
        // MiStatInterface.enableLog();

        // enable exception catcher.
        MiStatInterface.enableExceptionCatcher(true);

        // enable network monitor
        URLStatsRecorder.enableAutoRecord();
        URLStatsRecorder.setEventFilter(new HttpEventFilter() {

            @Override
            public HttpEvent onEvent(HttpEvent event) {
                Logger.t("MI_STAT").d(
                        event.getUrl() + " result =" + event.toJSON());
                // returns null if you want to drop this event.
                // you can modify it here too.
                return event;
            }
        });
        Logger.t("MI_STAT").d(
                MiStatInterface.getDeviceID(mApplication) + " is the device.");
    }

}

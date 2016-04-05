package com.telchina.init.image;

import android.text.TextUtils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.telchina.pub.image.FailedReason;
import com.telchina.pub.image.LoaderType;
import com.telchina.pub.utils.Config;

import java.io.File;

/**
 * Created by GISirFive on 2016-4-1.
 */
class Utils {
    /**能识别的URI种类*/
    public enum URI {
        HTTP("http://"), FILE("file://"), DRAWABLE("drawable://"), ASSETS("assets://"), CONTENT("content://");
        private String value;

        URI(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 根据图片加载方式获取对应的配置项
     *
     * @param type
     * @return
     */
    public static DisplayImageOptions getOptions(LoaderType type) {
        switch (type) {
            case _DEFAULT:
                return DisplayOptions._default;
            case _ORIGIN:
                return DisplayOptions._highLevel;
            case _MINI:
            case _MICRO:
                return DisplayOptions._thumbnail;
            case _ICON:
                return DisplayOptions._icon;
            default:
                return DisplayOptions._default;
        }
    }

    /**
     * 处理URI
     *
     * @param uri
     * @return
     * @author GISirFive
     */
    public static String translateURI(String uri) {
        if (TextUtils.isEmpty(uri) || uri.trim().isEmpty()) {
            throw new NullPointerException("传入的图片加载路径为空！");
        }
        boolean hasPrefix = false;
        for(URI u: URI.values()){
            if(uri.contains(u.getValue())){
                hasPrefix = true;
                break;
            }
        }
        if(!hasPrefix)
            uri = Config.get(Config.KEY.SERVER) + uri;
        return uri;
    }

    /**
     * 根据{@link LoaderType}获取Uri
     *
     * @param uri
     * @param type
     * @return
     */
    public static String getRealURI(String uri, LoaderType type) {
        switch (type) {
            case _MINI:
            case _MICRO:
            case _ICON:
                return uri + type.toString();
            default:
                return uri;
        }
    }


    /**
     * 判断指定LoaderType的图片是否缓存
     *
     * @param uri 原始图片访问地址
     * @return true-缓存于硬盘中
     */
    public static boolean isCacheExist(String uri, LoaderType type) {
        String tempUri = getRealURI(uri, type);
        File file = ImageLoader.getLoader().getDiskCache().get(tempUri);
        return (file != null && file.exists());
    }

    /**
     * 根据LoaderType获取标准的图片大小
     * @param type
     * @return
     */
    public static ImageSize getStandardImageSize(LoaderType type) {
        switch (type) {
            case _DEFAULT:
            case _ORIGIN:
                return null;
            case _MINI:
                return new ImageSize(360, 360);
            case _MICRO:
                return new ImageSize(240, 240);
            case _ICON:
                return new ImageSize(128, 128);
            default:
                return null;
        }
    }

    /**
     * 转换失败原因
     * @param failReason
     * @return
     */
    public static FailedReason getFailedReason(FailReason failReason){
        FailedReason.FailedType type = null;
        switch (failReason.getType()){
            case IO_ERROR:
                type = FailedReason.FailedType.IO_ERROR;
                break;
            case DECODING_ERROR:
                type = FailedReason.FailedType.DECODING_ERROR;
                break;
            case NETWORK_DENIED:
                type = FailedReason.FailedType.NETWORK_DENIED;
                break;
            case OUT_OF_MEMORY:
                type = FailedReason.FailedType.OUT_OF_MEMORY;
                break;
            case UNKNOWN:
                type = FailedReason.FailedType.UNKNOWN;
                break;
        }
        return new FailedReason(type, failReason.getCause());
    }

    /**
     * 判断当前地址要加载的图片是否位于本地
     * @param uri
     * @return
     */
    public static boolean isLocalURI(String uri){
        return uri.contains(URI.FILE.getValue()) || uri.contains(URI.CONTENT.getValue());
    }
}

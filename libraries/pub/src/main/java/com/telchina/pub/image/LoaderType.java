package com.telchina.pub.image;

/**
 * 按照哪种方式加载图片
 * Created by GISirFive on 2016-3-28.
 */
public enum LoaderType {
    /**默认，按照加载器的默认配置加载图片*/
    _DEFAULT,
    /**原图，不进行缩放和裁剪*/
    _ORIGIN,
    /**迷你型缩略图*/
    _MINI,
    /**微型缩略图（比迷你型更小的缩略图）*/
    _MICRO,
    /**图标*/
    _ICON,
}

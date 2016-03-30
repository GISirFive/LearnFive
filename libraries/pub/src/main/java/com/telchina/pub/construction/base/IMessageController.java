package com.telchina.pub.construction.base;

/**
 * 消息提示
 * Created by GISirFive on 2016-3-22.
 */
public interface IMessageController {

    /**
     * 显示一条消息提示
     * @param content
     * @param duration
     */
    void showToast(String content, int duration);

    /**
     * 显示一条消息对话框
     * @param content
     */
    void showMessageDialog(String content);
}

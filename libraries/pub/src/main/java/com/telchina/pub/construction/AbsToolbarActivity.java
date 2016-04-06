package com.telchina.pub.construction;

import android.support.v7.widget.Toolbar;

import com.telchina.pub.construction.base.ToolbarParam;

/**
 * Created by GISirFive on 2016-4-5.
 */
public abstract class AbsToolbarActivity extends AbsBaseActivity{

    @Override
    public boolean onToolbarConfiguration(Toolbar toolbar, ToolbarParam param) {
        toolbar.setContentInsetsRelative(0, 0);
        return true;
    }

}

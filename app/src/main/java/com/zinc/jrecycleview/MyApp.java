package com.zinc.jrecycleview;

import android.app.Application;

import com.zinc.jrecycleview.anim.AnimFactory;
import com.zinc.jrecycleview.config.JRecycleViewManager;

/**
 * author       : Jiang Pengyong
 * time         : 2018-04-08 23:07
 * email        : 56002982@qq.com
 * desc         : 应用application
 * version      : 1.0.0
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        JRecycleViewManager.getInstance()
                .setItemAnimations(AnimFactory.getAnimSet(AnimFactory.SLIDE_RIGHT))
                .setIsDebug(true);

    }
}

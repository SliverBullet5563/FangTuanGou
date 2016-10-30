package com.vmt.tuangou;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yolanda.nohttp.NoHttp;

import cn.bmob.v3.Bmob;

/**
 * Created by SilverBullet on 2016/10/1.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        NoHttp初始化
        NoHttp.initialize(this);

//        Fresco图片加载框架初始化
        Fresco.initialize(this);

//        Bmob的初始化
        Bmob.initialize(this, "3e7c6cf626f6ea78c7ae0ec75db214ef");
    }
}

package com.vmt.tuangou;

import android.app.Application;

import com.alipay.euler.andfix.patch.PatchManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.tauth.Tencent;
import com.yolanda.nohttp.NoHttp;

import java.io.IOException;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * Created by SilverBullet on 2016/10/1.
 */

public class MyApplication extends Application {
    private boolean flags = true;
    private Tencent mTencent;
    private PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();
//        NoHttp初始化
        NoHttp.initialize(this);

//        Fresco图片加载框架初始化
        Fresco.initialize(this);

//        Bmob的初始化
        Bmob.initialize(this, "3e7c6cf626f6ea78c7ae0ec75db214ef");

        if(flags == true) {
            flags = false;
            BmobUpdateAgent.initAppVersion();
        }
//        QQ登录
        mTencent = Tencent.createInstance("1105791054", this.getApplicationContext());

//        初始化patch管理类
        mPatchManager = new PatchManager(this);
        mPatchManager.init("1.0");//初始化patch版本
        mPatchManager.loadPatch();

        try {
            //添加布丁
            String path = "/data/data/com.vmt.tuangou/";
            mPatchManager.addPatch(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}













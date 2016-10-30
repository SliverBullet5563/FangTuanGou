package com.vmt.tuangou.listener;

import com.vmt.tuangou.entity.FavorInfo;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by SilverBullet on 2016/10/26.
 */

public abstract class BmobQueryAllCallback implements IBmobListener {
    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFailure() {

    }

    @Override
    public void querySuccess(FavorInfo favorInfo) {

    }

    @Override
    public void queryFailure(BmobException e) {

    }

}

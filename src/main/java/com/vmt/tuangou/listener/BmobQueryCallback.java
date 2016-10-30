package com.vmt.tuangou.listener;

import com.vmt.tuangou.entity.FavorInfo;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by SilverBullet on 2016/10/26.
 */

public abstract class BmobQueryCallback implements IBmobListener {

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFailure() {

    }


    @Override
    public void queryAllSuccess(List<FavorInfo> object) {

    }

    @Override
    public void queryAllFailure(BmobException e) {

    }
}

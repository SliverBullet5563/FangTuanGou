package com.vmt.tuangou.listener;

import com.vmt.tuangou.entity.FavorInfo;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by SilverBullet on 2016/10/26.
 */

public interface IBmobListener{
    void loginSuccess();
    void loginFailure();
    void querySuccess(FavorInfo favorInfo);
    void queryFailure(BmobException e);
    void queryAllSuccess(List<FavorInfo> object);
    void queryAllFailure(BmobException e);

}

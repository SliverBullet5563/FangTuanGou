package com.vmt.tuangou.listener;

import com.sina.weibo.sdk.openapi.models.User;

/**
 * Created by SilverBullet on 2016/10/31.
 */

public interface ISinaInfo {
    void getWBInfoSuccess(User user);
    void getWBInfoFalure();
}

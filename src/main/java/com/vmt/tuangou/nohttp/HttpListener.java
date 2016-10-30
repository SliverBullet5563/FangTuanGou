package com.vmt.tuangou.nohttp;

import com.yolanda.nohttp.rest.Response;

/**
 * Created by SilverBullet on 2016/10/3.
 */

public interface HttpListener<T> {
    void onSucceed(int what, Response response);
    void onFailed(int what, Response response);
}

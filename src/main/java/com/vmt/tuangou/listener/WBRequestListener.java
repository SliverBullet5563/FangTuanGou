package com.vmt.tuangou.listener;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.User;

/**
 * Created by SilverBullet on 2016/10/31.
 *
 *   微博 OpenAPI 回调接口。
 */
public class WBRequestListener implements RequestListener {

    private Context mContext;
    private ISinaInfo mISinaInfo;
    public WBRequestListener(Context context,ISinaInfo mISinaInfo) {
        mContext = context;
        this.mISinaInfo = mISinaInfo;
    }

    @Override
    public void onComplete(String response) {
        if (!TextUtils.isEmpty(response)) {
            // 调用 User#parse 将JSON串解析成User对象
            User user = User.parse(response);
            if (user != null) {
                mISinaInfo.getWBInfoSuccess(user);
            } else {
                mISinaInfo.getWBInfoFalure();
            }
        }
    }

    @Override
    public void onWeiboException(WeiboException e) {
        ErrorInfo info = ErrorInfo.parse(e.getMessage());
        Log.e("onWeiboException","onWeiboException"+info);
    }
}

package com.vmt.tuangou.listener;

/**
 * Created by SilverBullet on 2016/10/31.
 */

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.vmt.tuangou.R;
import com.vmt.tuangou.activity.LoginActivity;
import com.vmt.tuangou.core.AccessTokenKeeper;

/**
 * 微博认证授权回调类。
 * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
 * 该回调才会被执行。
 * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
 * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
 */
public class AuthListener implements WeiboAuthListener {

    private Oauth2AccessToken mAccessToken;
    private Context mContext;
    private ISinaLogin mSinaLogin;

    public AuthListener( Context context, Oauth2AccessToken accessToken,ISinaLogin sinaLogin) {
        mAccessToken = accessToken;
        mContext = context;
        mSinaLogin = sinaLogin;
    }

    @Override
    public void onComplete(Bundle values) {
        // 从 Bundle 中解析 Token
        mAccessToken = Oauth2AccessToken.parseAccessToken(values);
        //从这里获取用户输入的 电话号码信息
//        String phoneNum = mAccessToken.getPhoneNum();
        if (mAccessToken.isSessionValid()) {
            // 显示 Token
//                updateTokenView(false);

            // 保存 Token 到 SharedPreferences
            AccessTokenKeeper.writeAccessToken(mContext, mAccessToken);
            mSinaLogin.weiboLoginSuccess();

        } else {
            // 以下几种情况，您会收到 Code：
            // 1. 当您未在平台上注册的应用程序的包名与签名时；
            // 2. 当您注册的应用程序包名与签名不正确时；
            // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
            String code = values.getString("code");
            String message = mContext.getString(R.string.weibosdk_demo_toast_auth_failed);
            if (!TextUtils.isEmpty(code)) {
                message = message + "\nObtained the code: " + code;
            }
            mSinaLogin.weiboLoginFalure();
        }
    }
    @Override
    public void onCancel() {
        Toast.makeText(mContext,
                R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWeiboException(WeiboException e) {
        Log.e("onWeiboException","onWeiboException"+e.getMessage());
    }
}


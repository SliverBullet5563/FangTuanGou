package com.vmt.tuangou.core;

import android.app.Activity;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.vmt.tuangou.listener.AuthListener;
import com.vmt.tuangou.listener.ISinaInfo;
import com.vmt.tuangou.listener.ISinaLogin;
import com.vmt.tuangou.listener.WBRequestListener;

/**
 * Created by SilverBullet on 2016/10/31.
 */

public class WeiboUtils {
    /**
     * 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
     */
    private static SsoHandler mSsoHandler;
    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private static Oauth2AccessToken mAccessToken;
    private static AuthInfo mAuthInfo;
    /** 用户信息接口 */
    private static UsersAPI mUsersAPI;

    /***
     *    初始化微博
     * @param activity
     */
    public static void initWeibo(Activity activity){
        // 获取当前已保存过的 Token
        mAccessToken = AccessTokenKeeper.readAccessToken(activity);
        // 获取用户信息接口
        mUsersAPI = new UsersAPI(activity, Constants.APP_KEY, mAccessToken);
        // 创建微博实例
        mAuthInfo = new AuthInfo(activity, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(activity, mAuthInfo);
    }

    /***
     *  登录微博
     * @param activity
     * @param iSinaLogin
     */
    public static void loginWeibo(Activity activity, ISinaLogin iSinaLogin){
        mSsoHandler.authorizeWeb(new AuthListener(activity,mAccessToken,iSinaLogin));

    }

    /***
     *  获取微博用户信息
     * @param activity
     * @param iSinaInfo
     */
    public static void getWeiboInfo(Activity activity,ISinaInfo iSinaInfo){
        long uid = Long.parseLong(mAccessToken.getUid());
        mUsersAPI.show(uid, new WBRequestListener(activity,iSinaInfo));
    }

}





















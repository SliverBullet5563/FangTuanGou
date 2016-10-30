package com.vmt.tuangou.nohttp;

import android.content.Context;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created by SilverBullet on 2016/10/3.
 */

public class CallServer {

    private static CallServer callServer;
    private final RequestQueue mQueue;

    private CallServer(){
        mQueue = NoHttp.newRequestQueue();
    }

    public synchronized static CallServer getInstance(){
        if(callServer == null) {
            callServer = new CallServer();
        }
        return callServer;
    }

    /**
     *      添加一个请求到队列中
     */
    public <T> void add(Context context, int what, Request<T> request,
                        HttpListener<T> httpListener, boolean canCancle, boolean isLoading){

        mQueue.add(what,request,new HttpResponseListener<T>(context,request,httpListener,canCancle,isLoading));
    }



}

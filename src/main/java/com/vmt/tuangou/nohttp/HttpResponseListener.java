package com.vmt.tuangou.nohttp;

import android.content.Context;
import android.content.DialogInterface;

import com.vmt.tuangou.widget.WaitDialog;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by SilverBullet on 2016/10/3.
 */

public class HttpResponseListener<T> implements OnResponseListener<T> {

    private HttpListener<T> mListener;

    private WaitDialog mWaitDialog;

    private boolean isLoading;

    private Request<T> mRequest;

    public HttpResponseListener(Context context, Request<T> request, HttpListener<T> listener,
                                boolean canCancle, boolean isLoading) {
        this.mRequest = request;
        this.isLoading = isLoading;
        mListener = listener;
        if(context != null) {
            mWaitDialog = new WaitDialog(context);
            mWaitDialog.setCancelable(canCancle);
            mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mWaitDialog.cancel();
                }
            });

        }

    }

    @Override
    public void onStart(int what) {
        if(isLoading&&mWaitDialog!=null&&!mWaitDialog.isShowing()) {
            mWaitDialog.show();
        }
    }

    @Override
    public void onSucceed(int what, Response response) {
        if(mListener != null) {
            mListener.onSucceed(what,response);
        }

    }

    @Override
    public void onFailed(int what, Response response) {
        if(mListener != null) {
            mListener.onFailed(what,response);
        }

    }

    @Override
    public void onFinish(int what) {
        if(isLoading && mWaitDialog != null && mWaitDialog.isShowing()) {
            mWaitDialog.cancel();
        }

    }
}

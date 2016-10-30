package com.vmt.tuangou.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

/**
 * Created by SilverBullet on 2016/10/3.
 */

public class WaitDialog extends ProgressDialog {
    public WaitDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setProgressStyle(STYLE_SPINNER);
        setMessage("正在请求，请稍等...");
    }

    public WaitDialog(Context context, int theme) {
        super(context, theme);
    }
}

package com.vmt.tuangou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vmt.tuangou.R;
import com.vmt.tuangou.listener.MyTextWatcher;
import com.vmt.tuangou.nohttp.CallServer;
import com.vmt.tuangou.nohttp.HttpListener;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity implements HttpListener<String> {

    @InjectView(R.id.username)
    EditText mUsername;
    @InjectView(R.id.password)
    EditText mPassword;
    @InjectView(R.id.tv_quick_register)
    TextView mTvQuickRegister;
    @InjectView(R.id.tv_count_register)
    TextView mTvCountRegister;
    @InjectView(R.id.view_line_left)
    View mViewLineLeft;
    @InjectView(R.id.view_line_right)
    View mViewLineRight;
    //账号登录的布局
    @InjectView(R.id.ll_login)
    LinearLayout mLlLogin;
    //快速登录的布局
    @InjectView(R.id.ll_quick_login)
    LinearLayout mLlQuickLogin;
    @InjectView(R.id.ll_forget_pwd)
    LinearLayout mLlForgetPwd;
    @InjectView(R.id.et_quick_phone)
    EditText mEtQuickPhone;
    @InjectView(R.id.et_quick_code)
    EditText mEtQuickCode;
    @InjectView(R.id.quick_login_btn)
    Button mBtnLogin;
    @InjectView(R.id.btn_get_code)
    Button mBtnGetCode;

    private Animation mAnimationRight;
    private Animation mAnimationLeft;

    private int mOrange;
    private int mGary;
    /**
     * 帐号名密码是否为空
     **/
    private boolean isUsernameNull = false;
    private boolean isPwdNull = false;
    /**
     * 手机号验证码是否为空
     **/
    private boolean isPhoneNull = false;
    private boolean isCodeNull = false;

    /**
     * 倒计时秒数
     */
    private int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        init();
        initAnimation();

    }

    private void init() {
        mOrange = getResources().getColor(R.color.orange);
        mGary = getResources().getColor(R.color.gray);
        //文本改变状态
        mEtQuickPhone.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                isPhoneNull = TextUtils.isEmpty(s.toString()) ? false : true;
                mBtnLogin.setEnabled((isPhoneNull && isCodeNull) ? true : false);
            }
        });
        mEtQuickCode.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                isCodeNull = TextUtils.isEmpty(s.toString()) ? false : true;
                mBtnLogin.setEnabled((isPhoneNull && isCodeNull) ? true : false);
            }
        });
    }
    /**验证码计时**/
    public void countDownTimer(){
        mBtnGetCode.setEnabled(false);
        mCount = 60;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCount--;
                        mBtnGetCode.setText(mCount + "");
                        if (mCount <= 0) {
                            mBtnGetCode.setText("重新发送");
                            mBtnGetCode.setEnabled(true);
                            timer.cancel();
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

    /***
     * 初始化动画
     */
    private void initAnimation() {

        //tab底部线条往左移动
        mAnimationRight = AnimationUtils.loadAnimation(this, R.anim.view_line_move_left);
        mAnimationRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mTvQuickRegister.setTextColor(mOrange);
                mTvCountRegister.setTextColor(mGary);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //布局的显示和隐藏
                mViewLineLeft.setVisibility(View.VISIBLE);
                mViewLineRight.setVisibility(View.INVISIBLE);
                mLlLogin.setVisibility(View.GONE);
                mLlQuickLogin.setVisibility(View.VISIBLE);
                mLlForgetPwd.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //tab底部线条往右移动
        mAnimationLeft = AnimationUtils.loadAnimation(this, R.anim.view_line_move_right);
        mAnimationLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mTvQuickRegister.setTextColor(mGary);
                mTvCountRegister.setTextColor(mOrange);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //布局的显示和隐藏
                mViewLineLeft.setVisibility(View.INVISIBLE);
                mViewLineRight.setVisibility(View.VISIBLE);
                mLlLogin.setVisibility(View.VISIBLE);
                mLlQuickLogin.setVisibility(View.GONE);
                mLlForgetPwd.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    public void login() {
//        trim Trim() 函数的功能是去掉首尾空格
        String username = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "您的用户名或密码有误！", Toast.LENGTH_SHORT).show();
            return;
        }
        Request<String> request = NoHttp.createStringRequest("https://api.bmob.cn/1/users", RequestMethod.POST);
        //添加头部
        request.addHeader("X-Bmob-Application-Id", "3e7c6cf626f6ea78c7ae0ec75db214ef");
        request.addHeader("X-Bmob-REST-API-Key", "7e95f7332e36f257ab5110941a22c4cc");
        //添加body
        request.setDefineRequestBodyForJson("{\"username\" : \"123456\", \"password\" : \"123456\"}");

        // {"username" : "123456", "password" : "123456"}
        CallServer.getInstance().add(LoginActivity.this, 0, request, this, true, true);
    }

    @Override
    public void onSucceed(int what, Response response) {
        switch (what) {
            case 0:
                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    @Override
    public void onFailed(int what, Response response) {
        switch (what) {
            case 0:
                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    @OnClick({R.id.tv_quick_register, R.id.tv_count_register, R.id.tv_register,R.id.login_btn,R.id.btn_get_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.tv_quick_register:
                mViewLineRight.startAnimation(mAnimationRight);
                break;
            case R.id.tv_count_register:
                mViewLineLeft.startAnimation(mAnimationLeft);
                break;
            case R.id.tv_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_get_code:
                countDownTimer();
                break;
        }
    }
}











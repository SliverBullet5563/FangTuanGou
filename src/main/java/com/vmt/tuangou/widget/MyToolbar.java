package com.vmt.tuangou.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vmt.tuangou.R;



/**
 * Created by SilverBullet on 2016/10/19.
 */

public class MyToolbar extends Toolbar {


    private TextView tvCity;
    private EditText mSearchView;
    private ImageView navButton;
    private View mView;
    private LayoutInflater mInflater;


    public MyToolbar(Context context) {
        this(context,null);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
        setContentInsetsRelative(10,10);

        if(attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.MyToolbar, defStyleAttr, 0);
            final Drawable rightButton = a.getDrawable(R.styleable.MyToolbar_rightButton);

            if(rightButton != null) {
                setRightButton(rightButton);
            }

            a.recycle();
        }


    }

    private void setRightButton(Drawable icon) {
        if(navButton != null) {
            navButton.setImageDrawable(icon);
            navButton.setVisibility(VISIBLE);
        }
    }

    public void initView() {
        if(mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.item_toolbar,null);
            tvCity = (TextView) mView.findViewById(R.id.tv_city);
            mSearchView = (EditText)mView.findViewById(R.id.search_view);
            navButton = (ImageView) mView.findViewById(R.id.imageButton);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView,lp);
        }

    }

    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        if(tvCity != null) {
            tvCity.setText(title);
            showTitleView();
        }
    }
    public void showTitleView(){
        if(tvCity !=null)
            tvCity.setVisibility(VISIBLE);
    }
}

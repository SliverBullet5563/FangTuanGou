package com.vmt.tuangou.adapter;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SilverBullet on 2016/10/11.
 * 抽取的ListView的适配器
 * abstract：想要继承自BaseAdapter但又不想去重写BaseAdapter的方法，可以声明为抽象的
 */

public abstract class CommenAdapter<T> extends BaseAdapter {
    //      获得到mData，从构造方法中
    List<T> mData = new ArrayList<>();

    public CommenAdapter(List<T> data) {
        mData = data;
    }

    //    getCount的数值是会改变的
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}

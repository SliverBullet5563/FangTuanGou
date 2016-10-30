package com.vmt.tuangou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vmt.tuangou.R;
import com.vmt.tuangou.entity.ShopInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SilverBullet on 2016/10/4.
 */

public class MyGridAdapter extends BaseAdapter {

    private Context mContext;

    private List<ShopInfo> itemData = new ArrayList<>();

    public MyGridAdapter(Context context, List<ShopInfo> itemData) {
        mContext = context;
        this.itemData = itemData;
    }




    @Override
    public int getCount() {
        return itemData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.iv);
        TextView textView = (TextView) inflate.findViewById(R.id.tv);
        imageView.setImageResource(itemData.get(position).getResId());
        textView.setText(itemData.get(position).getName());
        return inflate;
    }
}

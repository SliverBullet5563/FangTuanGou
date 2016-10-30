package com.vmt.tuangou.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SilverBullet on 2016/10/11.
 *
 *      通用的ViewHolder，复用组件，免去多次findViewById,通过控件id查找控件
 *   需要把控件id和控件匹配起来
 *
 */

public class ViewHolder {
    private final View mConvertView;
    //    1.效率比较高
//    2.key只能是integer
    SparseArray<View> mViews = null;

    public ViewHolder(Context context, int layoutID, ViewGroup parent) {
        mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutID, parent,false);
        mConvertView.setTag(this);
    }

    /***
     *      获取一个ViewHolder对象
     * @param context
     * @param layoutID
     * @param convertView
     * @param parent
     * @return
     */
    public static ViewHolder get(Context context, int layoutID, View convertView, ViewGroup parent){
        if(convertView == null) {
            return new ViewHolder(context,layoutID,parent);
        }else {
            return (ViewHolder) convertView.getTag();
        }
    }

    public <T extends View> T getView(int viewID){


        View view = mViews.get(viewID);
        if(view == null) {
            view = mConvertView.findViewById(viewID);
            mViews.put(viewID,view);
        }
        return (T)view;
    }

    public View getConvertView(){
        return mConvertView;
    }



}
















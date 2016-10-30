package com.vmt.tuangou.entity;

/**
 * Created by SilverBullet on 2016/10/4.
 */

public class ShopInfo {

    private String name;
    private int resId;

    public ShopInfo(String name, int resId) {
        this.name = name;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }


}

package com.vmt.tuangou.entity;

/**
 * Created by SilverBullet on 2016/10/26.
 */

public class FavorInfo extends BaseModel{
    private String goods_id;
    private String proudct;
    private String price;
    private String value;
    private boolean isFavor;
    private String short_title;
    private String iamge_url;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getProudct() {
        return proudct;
    }

    public void setProudct(String proudct) {
        this.proudct = proudct;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isFavor() {
        return isFavor;
    }

    public void setFavor(boolean favor) {
        isFavor = favor;
    }

    public String getShort_title() {
        return short_title;
    }

    public void setShort_title(String short_title) {
        this.short_title = short_title;
    }

    public String getIamge_url() {
        return iamge_url;
    }

    public void setIamge_url(String iamge_url) {
        this.iamge_url = iamge_url;
    }
}

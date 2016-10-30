package com.vmt.tuangou.utils;

import android.util.Log;
import android.view.View;

import com.vmt.tuangou.entity.BaseModel;
import com.vmt.tuangou.entity.FavorInfo;
import com.vmt.tuangou.listener.IBmobListener;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by SilverBullet on 2016/10/25.
 * 单利模式
 */

public final class BmobManager extends BaseModel {

    private static BmobManager manager = null;
    private String objId;
    private static IBmobListener mListener;

    public synchronized static BmobManager getInstance(IBmobListener listener) {
        mListener = listener;
        if (manager == null) {
            return new BmobManager();
        }
        return manager;
    }

    /*public void initListener(IBmobListener listener) {
        mListener = listener;
    }*/

    public void insertData(BaseModel model) {

        model.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    objId = objectId;
                    Log.e("insertData", "收藏成功：");
                } else {
                    Log.i("insertData", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void deleteData(BaseModel model) {
        model.setObjectId(objId);
        model.delete(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob", "成功");
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public void updateData(View view) {

    }

    /**
     * 根据Id查询数据
     *
     * @param favorInfo
     */
    public void queryData(FavorInfo favorInfo) {
        BmobQuery<FavorInfo> query = new BmobQuery<FavorInfo>();
        query.getObject(objId, new QueryListener<FavorInfo>() {

            @Override
            public void done(FavorInfo object, BmobException e) {
                if (e == null) {
                    mListener.querySuccess(object);
                } else {
                    mListener.queryFailure(e);
                }
            }

        });
    }

    public void queryAllData(String queryKey, Object queryValue) {
        BmobQuery<FavorInfo> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo(queryKey, queryValue);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //执行查询方法, 如果有后续操作，定义一个回调监听
        query.findObjects(new FindListener<FavorInfo>() {
            @Override
            public void done(List<FavorInfo> object, BmobException e) {
                if (e == null) {
                    mListener.queryAllSuccess(object);
                } else {
                    mListener.queryAllFailure(e);
                }
            }
        });
    }
}

















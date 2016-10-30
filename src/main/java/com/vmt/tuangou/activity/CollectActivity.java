package com.vmt.tuangou.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.vmt.tuangou.R;
import com.vmt.tuangou.adapter.CommenAdapter;
import com.vmt.tuangou.entity.FavorInfo;
import com.vmt.tuangou.listener.BmobQueryAllCallback;
import com.vmt.tuangou.utils.BmobManager;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.exception.BmobException;

public class CollectActivity extends AppCompatActivity {

    @InjectView(R.id.iv_back)
    ImageView mIvBack;
    @InjectView(R.id.list_collect)
    ListView mListCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.inject(this);

        BmobManager.getInstance(new BmobQueryAllCallback() {
            @Override
            public void queryAllSuccess(final List<FavorInfo> object) {

                mListCollect.setAdapter(new CommenAdapter<FavorInfo>(object) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View collectView = getLayoutInflater().inflate(R.layout.good_list_item, parent,false);
                        TextView tvTitle = (TextView) collectView.findViewById(R.id.title);
                        tvTitle.setText(object.get(position).getProudct());
                        return collectView;
                    }
                });
            }

            @Override
            public void queryAllFailure(BmobException e) {

            }
        }).queryAllData("favor",true);

    }
}

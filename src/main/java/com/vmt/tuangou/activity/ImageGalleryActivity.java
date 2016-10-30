package com.vmt.tuangou.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.vmt.tuangou.R;
import com.vmt.tuangou.adapter.MyPagerAdapter;
import com.vmt.tuangou.entity.DetailInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ImageGalleryActivity extends AppCompatActivity {
    @InjectView(R.id.pager_iamge)
    ViewPager mPagerIamge;

    private List<View> mViewList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        DetailInfo detailInfo = (DetailInfo) intent.getSerializableExtra("detailInfo");
        List<String> detailImags = detailInfo.getResult().getDetail_imags();

        for(int i = 0; i < detailImags.size(); i++) {
            View pagerImageView = getLayoutInflater().inflate(R.layout.pager_image_item, null);
            SimpleDraweeView ivItem = (SimpleDraweeView) pagerImageView.findViewById(R.id.iv_item);
            Uri uri = Uri.parse(detailImags.get(i));
            ivItem.setImageURI(uri);
            mViewList.add(pagerImageView);
        }
        mPagerIamge.setAdapter(new MyPagerAdapter(mViewList));
    }

}

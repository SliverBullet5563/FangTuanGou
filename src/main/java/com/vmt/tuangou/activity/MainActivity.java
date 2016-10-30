package com.vmt.tuangou.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vmt.tuangou.fragment.AroundFragment;
import com.vmt.tuangou.fragment.MainFragment;
import com.vmt.tuangou.fragment.MineFragment;
import com.vmt.tuangou.fragment.MoreFragment;
import com.vmt.tuangou.R;

public class MainActivity extends FragmentActivity {

    private Class[] fragments = new Class[]{MainFragment.class,AroundFragment.class,
            MineFragment.class, MoreFragment.class};
    private int[] imgSelectors = new int[]{
            R.drawable.ic_tab_artists,
            R.drawable.ic_tab_albums,
            R.drawable.ic_tab_songs,
            R.drawable.ic_tab_playlists
    };
    private String[] tabTitle = new String[]{
            "首页","周边","我的","更多"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        tabHost.setup(MainActivity.this, getSupportFragmentManager(), android.R.id.tabcontent);

        for(int i = 0; i < fragments.length; i++) {

            View inflate = getLayoutInflater().inflate(R.layout.tab_item, null);
            ImageView tab_iv = (ImageView) inflate.findViewById(R.id.iv);
            TextView tab_tv = (TextView) inflate.findViewById(R.id.tv);
            tab_iv.setImageResource(imgSelectors[i]);
            tab_tv.setText(tabTitle[i]);

            tabHost.addTab(tabHost.newTabSpec(""+i).setIndicator("tab1").setIndicator(inflate), fragments[i], null);
        }



    }
}















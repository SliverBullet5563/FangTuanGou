package com.vmt.tuangou.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vmt.tuangou.R;
import com.vmt.tuangou.activity.CollectActivity;
import com.vmt.tuangou.activity.LoginActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {


    @InjectView(R.id.btn_login)
    Button mBtnLogin;
    @InjectView(R.id.tv_collect)
    TextView mTvCollect;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick({R.id.btn_login,R.id.tv_collect})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.btn_login :
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_collect:
                Intent intent1 = new Intent(getActivity(), CollectActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
    /*public void login(View view) {

    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

package com.vmt.tuangou.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.vmt.tuangou.R;
import com.vmt.tuangou.activity.LocationActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class AroundFragment extends Fragment {


    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.supplier_list_product_tv)
    TextView mProductTv;
    @InjectView(R.id.supplier_list_product)
    LinearLayout mProduct;
    @InjectView(R.id.supplier_list_sort_tv)
    TextView mSortTv;
    @InjectView(R.id.supplier_list_sort)
    LinearLayout mSort;
    @InjectView(R.id.supplier_list_activity_tv)
    TextView mActivityTv;
    @InjectView(R.id.supplier_list_activity)
    LinearLayout mActivity;
    private ArrayList<Map<String, String>> mMenuData3;
    private ArrayList<Map<String, String>> mMenuData2;
    private ArrayList<Map<String, String>> mMenuData1;
    private ListView mPopListView;
    private PopupWindow mPopupMenu;
    private SimpleAdapter mMenuAdapter1;
    private SimpleAdapter mMenuAdapter2;
    private SimpleAdapter mMenuAdapter3;
    private int menuIndex = 0;

    public AroundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.activity_supplier_list, container, false);
        ButterKnife.inject(this, inflate);
//        toolbar设置菜单按钮
        mToolbar.inflateMenu(R.menu.around_title_ment);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit :
                            Intent intent = new Intent(getActivity(), LocationActivity.class);
                            startActivity(intent);
                        break;
                    
                    default:
                        break;
                }
                return true;
            }
        });

        initData();
        initPopMenu();

        return inflate;
    }



    /**
     *  初始化popupwindow数据
     */
    private void initData() {


        mMenuData1 = new ArrayList<>();
        String[] menuStr = new String[]{"全部","粮油","衣服","图书","电子产品", "酒水饮料", "水果"};
        Map<String,String> map1;
        for(int i = 0; i < menuStr.length; i++) {
            map1 = new HashMap<>();
            map1.put("name",menuStr[i]);
            mMenuData1.add(map1);
        }

        mMenuData2 = new ArrayList<>();
        String[] menuStr2 = new String[]{"综合排序", "配送费最低"};
        Map<String,String> map2;
        for(int i = 0; i < menuStr2.length; i++) {
            map2 = new HashMap<>();
            map2.put("name",menuStr2[i]);
            mMenuData2.add(map2);
        }

        mMenuData3 = new ArrayList<>();
        String[] menuStr3 = new String[]{"优惠活动", "特价活动", "免配送费",
                "可在线支付"};
        Map<String,String> map3;
        for(int i = 0; i < menuStr3.length; i++) {
            map3 = new HashMap<>();
            map3.put("name",menuStr3[i]);
            mMenuData3.add(map3);
        }

    }

    /***
     *     初始化popupwindow
     */
    private void initPopMenu() {
        View popView = LayoutInflater.from(getActivity()).inflate(R.layout.popwin_supplier_list, null);
        mPopListView = (ListView) popView.findViewById(R.id.popwin_supplier_list_lv);
        mPopupMenu = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
//        外部点击
        mPopupMenu.setOutsideTouchable(true);

        mPopupMenu.setBackgroundDrawable(new BitmapDrawable());
        mPopupMenu.setFocusable(true);
        mPopupMenu.setAnimationStyle(R.style.popwin_anim_style);
        mPopupMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mProductTv.setTextColor(Color.parseColor("#5a5959"));
                mSortTv.setTextColor(Color.parseColor("#5a5959"));
                mActivityTv.setTextColor(Color.parseColor("#5a5959"));
            }
        });
        LinearLayout line = (LinearLayout) popView.findViewById(R.id.popwin_supplier_list_bottom);
        line.setBackgroundColor(Color.BLUE);
        popView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopupMenu.dismiss();
                    }
                });

        mMenuAdapter1 = new SimpleAdapter(getActivity(), mMenuData1, R.layout.item_listview_popwin, new String[]{"name"},
                new int[]{R.id.listview_popwind_tv});
        mMenuAdapter2 = new SimpleAdapter(getActivity(), mMenuData2, R.layout.item_listview_popwin, new String[]{"name"},
                new int[]{R.id.listview_popwind_tv});
        mMenuAdapter3 = new SimpleAdapter(getActivity(), mMenuData3, R.layout.item_listview_popwin, new String[]{"name"},
                new int[]{R.id.listview_popwind_tv});

        mPopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopupMenu.dismiss();
//                设置标志值
                switch (menuIndex) {
                    case 0 :
                        String currentProduct = mMenuData1.get(position).get("name");
                        mProductTv.setText(currentProduct);

                        break;
                    case 1:
                        String currentSort = mMenuData2.get(position).get("name");
                        mSortTv.setText(currentSort);
                        break;
                    case 2:
                        String currentActivity = mMenuData3.get(position).get("name");
                        mActivityTv.setText(currentActivity);
                        break;

                    default:
                        break;
                }
            }
        });

    }


    @OnClick({R.id.supplier_list_product, R.id.supplier_list_sort, R.id.supplier_list_activity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.supplier_list_product:
                mProductTv.setTextColor(Color.parseColor("#39ac69"));
                mPopListView.setAdapter(mMenuAdapter1);
//                ? ? ?
                mPopupMenu.showAsDropDown(mProduct,0,2);
//                点击事件里给“标志值”赋值
                menuIndex = 0;
                break;
            case R.id.supplier_list_sort:
                mSortTv.setTextColor(Color.parseColor("#39ac69"));
                mPopListView.setAdapter(mMenuAdapter2);
                mPopupMenu.showAsDropDown(mSort,0,2);


                menuIndex = 1;
                break;
            case R.id.supplier_list_activity:
                mActivityTv.setTextColor(Color.parseColor("#39ac69"));
                mPopListView.setAdapter(mMenuAdapter3);

                mPopupMenu.showAsDropDown(mActivity,0,2);
                menuIndex = 2;
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }



    /*@OnClick(R.id.btn_get)
    public void NoHttp_get(View view){

        String url = "http://www.baidu.com";
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.GET);
        CallServer.getInstance().add(getActivity(),0,request,this,true,true);

    }*/

    /*@Override
    public void onSucceed(int what, Response response) {
        switch (what) {
            case 0 :
                    Toast.makeText(getActivity(), ""+response.get(), Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    @Override
    public void onFailed(int what, Response response) {

    }*/
}

package com.vmt.tuangou.fragment;


import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.vmt.tuangou.R;
import com.vmt.tuangou.activity.DetailActivity;
import com.vmt.tuangou.adapter.CommenAdapter;
import com.vmt.tuangou.adapter.MyGridAdapter;
import com.vmt.tuangou.adapter.MyPagerAdapter;
import com.vmt.tuangou.adapter.ViewHolder;
import com.vmt.tuangou.bean.ContantsPool;
import com.vmt.tuangou.entity.GoodsInfo;
import com.vmt.tuangou.entity.ShopInfo;
import com.vmt.tuangou.nohttp.CallServer;
import com.vmt.tuangou.nohttp.HttpListener;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements ContantsPool, HttpListener<String> {


    @InjectView(R.id.listView)
    PullToRefreshListView mPullToRefreshListView;
    private List<View> mViews = new ArrayList<>();
    /**
     * gridView两页的数据
     */
    private List<ShopInfo> OnePageView = new ArrayList<>();
    private List<ShopInfo> TwoPageView = new ArrayList<>();


    private View mInflate;



//    自定义的商品存放容器
    private List<GoodsInfo.ResultBean.GoodlistBean> mDataList = new ArrayList<>();
    private MyAdapter mAdapter;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.inject(this,mInflate);
            initData();
            initGridView();

        }
        return mInflate;
    }

    private void initGridView() {
        //listview的头部
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.home_headviewall, null);
        ViewPager viewPager = (ViewPager) headView.findViewById(R.id.viewPager);
//        viewPager = (ViewPager) mInflate.findViewById(R.id.viewPager);

        //第一页数据
        View pagerOne = LayoutInflater.from(getActivity()).inflate(R.layout.gridview,null);
        GridView gridView = (GridView) pagerOne.findViewById(R.id.gridView);
        gridView.setAdapter(new MyGridAdapter(getActivity(),OnePageView));

        //第二页数据
        View pagerTwo = LayoutInflater.from(getActivity()).inflate(R.layout.gridview,null);
        GridView gridView2 = (GridView) pagerTwo.findViewById(R.id.gridView);
        gridView2.setAdapter(new MyGridAdapter(getActivity(),TwoPageView));

        mViews.add(pagerOne);
        mViews.add(pagerTwo);
        viewPager.setAdapter(new MyPagerAdapter(mViews));

//        viewPager.setAdapter(new MyPagerAdapter(mViews);
        //添加listview的头部
        //将下拉刷新转换成listview
        ListView listView = mPullToRefreshListView.getRefreshableView();
        listView.addHeaderView(headView);
        mAdapter = new MyAdapter(mDataList);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new MyTask().execute();
            }
        });
        mPullToRefreshListView.setAdapter(mAdapter);

        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("goods_id",mDataList.get(position-1).getGoods_id());
                startActivity(intent);
            }
        });
    }

    class MyAdapter extends CommenAdapter{

        public MyAdapter(List data) {
            super(data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

//            ViewHolder holder = null;
//            第二部：判断条件的封装
//            if(convertView == null) {
//                holder = new ViewHolder(getActivity(),R.layout.good_list_item,parent);
//                第一步：
//                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.good_list_item, null);
//                convertView.setTag(holder);

//            }else {
//                holder  = (ViewHolder) convertView.getTag();
//            }

            ViewHolder holder = ViewHolder.get(getActivity(), R.layout.good_list_item, convertView, parent);


//            第三步
//            商品子视图
//            holder.tvTitle = (TextView) convertView.findViewById(R.id.title);
//            holder.tvTitle.setText(mDataList.get(position).getTitle());
            TextView tv = holder.getView(R.id.title);
            tv.setText(mDataList.get(position).getProduct());

            Uri uri = Uri.parse(mDataList.get(position).getImages().get(0).getImage());
//            SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
            SimpleDraweeView draweeView = holder.getView(R.id.photo);
            draweeView.setImageURI(uri);

            return holder.getConvertView();
        }
    }
    /*static class ViewHolder{
        TextView tvTitle;
    }*/

    private void initData() {
        //获取资源文件的数据
        String[] array = getResources().getStringArray(R.array.home_bar_labels);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.home_bar_icon);
        //初始化商品分类的数据
        for(int i = 0; i < array.length; i++) {
            if(i < 8) {
                OnePageView.add(new ShopInfo(array[i],typedArray.getResourceId(i,0)));
            }else {
                TwoPageView.add(new ShopInfo(array[i],typedArray.getResourceId(i,0)));
            }
        }


//      猜你喜欢的请求
        Request<String> request = NoHttp.createStringRequest(spRecommendURL, RequestMethod.GET);
        CallServer.getInstance().add(getActivity(),0,request,this,true,true);
        /**热门电影的请求**/
        Request<String> filmRequest = NoHttp.createStringRequest(filmHotUrl, RequestMethod.GET);
        CallServer.getInstance().add(getActivity(), 1, filmRequest, this, true, true);
    }

    @Override
    public void onSucceed(int what, Response response) {
        switch (what) {
            case 0 :
                Gson gson = new Gson();
                GoodsInfo goodsInfo = gson.fromJson((String) response.get(), GoodsInfo.class);
                List<GoodsInfo.ResultBean.GoodlistBean> goodlist = goodsInfo.getResult().getGoodlist();

                mDataList.addAll(goodlist);
                mAdapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }

    @Override
    public void onFailed(int what, Response response) {

    }

    class MyTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        //更新UI操作
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mPullToRefreshListView.onRefreshComplete();
        }
    }

}











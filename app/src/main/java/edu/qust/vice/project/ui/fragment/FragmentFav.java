package edu.qust.vice.project.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.qust.vice.project.MyApplication;
import edu.qust.vice.project.utils.NetUtil;
import tsing.zhong.fu.frameworkforproject_skilltree_.R;

/**
 * Created by neokree on 16/12/14.
 *
 */
public class FragmentFav extends Fragment{


    @ViewInject(R.id.recycler_view)
    private RecyclerView recyclerView;

    @ViewInject(R.id.swipe_container)
    private SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<Data> datas;
    private Adapter_RecyclerView adapter;

    public FragmentFav() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weight_demo_recyclerview, container, false);
        ViewUtils.inject(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        //设置动画颜色
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);


        //创建默认的线性LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        datas = new ArrayList<>();
        adapter = new Adapter_RecyclerView(datas);
        recyclerView.setAdapter(adapter);
        getprocess();
        //添加条目监听
        adapter.setOnItemClickListener(new Adapter_RecyclerView.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                String id = view.getTag().toString();
                Toast.makeText(getActivity(), data + id, Toast.LENGTH_SHORT).show();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                getprocess();
            }
        });


    }


    void getprocess() {
        datas.clear();
        NetUtil.get("?c=api&_table=course&_interface=getitem&uid=" + ((MyApplication) getActivity().getApplication()).u.getId()
                , null
                , new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (!response.get("data").equals(false)) {
                        JSONArray jarray = response.getJSONArray("data");
                        for (int i = 0; i < jarray.length(); ++i) {
                            datas.add(new Data(jarray.getJSONObject(i).getString("course_name"), jarray.getJSONObject(i).getString("course_id")));
                        }
                        adapter.notifyDataSetChanged();
                        if (swipeRefreshLayout!=null) swipeRefreshLayout.setRefreshing(false);
                    }
                    super.onSuccess(statusCode, headers, response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    class Data {
        private String name,id;
        Data(String name,String id) {
            this.name = name;
            this.id   = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}

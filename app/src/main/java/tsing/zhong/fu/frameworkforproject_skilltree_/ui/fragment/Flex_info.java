/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tsing.zhong.fu.frameworkforproject_skilltree_.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nineoldandroids.view.ViewHelper;

import org.apache.http.Header;
import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tsing.zhong.fu.frameworkforproject_skilltree_.R;
import tsing.zhong.fu.frameworkforproject_skilltree_.ui.FlexibleSpaceWithImageWithViewPagerTabActivity;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.NetUtil;


public class Flex_info extends FlexibleSpaceWithImageBaseFragment<ObservableRecyclerView> {

    InfoRecyclerAdapter adapter;
    String cid = null,uid=null;
    List<InfoData> ds;
    SwipeRefreshLayout swipeRefreshLayout;
    public Flex_info() {
        ds = new ArrayList<InfoData>();
    }
    @Override
    public void setCid(String cid,String uid) {
        this.cid = cid;
        this.uid = uid;
        if (adapter != null){
            NetUtil.get("?c=api&_table=course&_interface=getdata&course_id=" + this.cid + "&user_id=" + this.uid, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            if (!jsonArray.get(0).equals(false)) {
                                ds.add(new InfoData("effort", jsonArray.getJSONArray(0).getJSONObject(0).getInt("effort")));
                                ds.add(new InfoData("talent", jsonArray.getJSONArray(1).getJSONObject(0).getInt("talent")));
                                ds.add(new InfoData("excited", jsonArray.getJSONArray(2).getJSONObject(0).getInt("excited")));
                            }
                            ds.add(new InfoData("学习人数", jsonArray.getJSONArray(3).getJSONObject(0).getInt("COUNT(*)")));
                            if (jsonArray.get(4).equals(false)){
                                ds.add(new InfoData("学习进度", -1));
                            } else {
                                ds.add(new InfoData("学习进度", jsonArray.getJSONArray(4).getJSONObject(0).getInt("course_state")));
                            }
                        if (swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
                            adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    super.onSuccess(statusCode, headers, response);
                }
            });
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_flexiblespacewithimagerecyclerview_nofab, container, false);
        final ObservableRecyclerView recyclerView = (ObservableRecyclerView) view.findViewById(R.id.scroll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);
        final View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.recycler_header, null);
        final int flexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        headerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, flexibleSpaceImageHeight));
        adapter = new InfoRecyclerAdapter(getActivity(), ds, headerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setTouchInterceptionViewGroup((ViewGroup) view.findViewById(R.id.fragment_root));

        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_SCROLL_Y)) {
            final int scrollY = args.getInt(ARG_SCROLL_Y, 0);
            ScrollUtils.addOnGlobalLayoutListener(recyclerView, new Runnable() {
                @Override
                public void run() {
                    int offset = scrollY % flexibleSpaceImageHeight;
                    RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
                    if (lm != null && lm instanceof LinearLayoutManager) {
                        ((LinearLayoutManager) lm).scrollToPositionWithOffset(0, -offset);
                    }
                }
            });
            updateFlexibleSpace(scrollY, view);
        } else {
            updateFlexibleSpace(0, view);
        }

        recyclerView.setScrollViewCallbacks(this);
        setCid(this.cid,this.uid);
        return view;
    }

    @Override
    public void setScrollY(int scrollY, int threshold) {
        View view = getView();
        if (view == null) {
            return;
        }
        ObservableRecyclerView recyclerView = (ObservableRecyclerView) view.findViewById(R.id.scroll);
        if (recyclerView == null) {
            return;
        }
        View firstVisibleChild = recyclerView.getChildAt(0);
        if (firstVisibleChild != null) {
            int offset = scrollY;
            int position = 0;
            if (threshold < scrollY) {
                int baseHeight = firstVisibleChild.getHeight();
                position = scrollY / baseHeight;
                offset = scrollY % baseHeight;
            }
            RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
            if (lm != null && lm instanceof LinearLayoutManager) {
                ((LinearLayoutManager) lm).scrollToPositionWithOffset(position, -offset);
            }
        }
    }

    @Override
    protected void updateFlexibleSpace(int scrollY, View view) {
        int flexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);

        View recyclerViewBackground = view.findViewById(R.id.list_background);

        // Translate list background
        ViewHelper.setTranslationY(recyclerViewBackground, Math.max(0, -scrollY + flexibleSpaceImageHeight));

        // Also pass this event to parent Activity
        FlexibleSpaceWithImageWithViewPagerTabActivity parentActivity =
                (FlexibleSpaceWithImageWithViewPagerTabActivity) getActivity();
        if (parentActivity != null) {
            parentActivity.onScrollChanged(scrollY, (ObservableRecyclerView) view.findViewById(R.id.scroll));
        }
    }

}

class InfoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private LayoutInflater mInflater;
    private List<InfoData> mItems;
    private View mHeaderView;

    public InfoRecyclerAdapter(Context context, List<InfoData> items, View headerView) {
        mInflater = LayoutInflater.from(context);
        mItems = items;
        mHeaderView = headerView;
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            return 1;
        } else {
            return 1 + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        } else {
            return new ItemViewHolder(mInflater.inflate(R.layout.course_info, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {
            BarChart chart = ((ItemViewHolder) viewHolder).chart;
            int[] colors = {
                     0xFFF44336
                    ,0xFF3F51B5
                    ,0xFF009688
                    ,0xFF2196F3
                    ,0xFF9C27B0};
            for (int i = 0;i<mItems.size();++i) {
                chart.addBar(new BarModel(
                        mItems.get(i).getName(),
                        mItems.get(i).getV(), colors[i]));
            }
            chart.startAnimation();
            //uid = mItems.get(position - 1).getName();
            //date = mItems.get(position - 1).getDate();
            //date = DateFormat.getDateTimeInstance().format(new Date(Long.parseLong(date)));
            //((ItemViewHolder) viewHolder).content.setText(mItems.get(position - 1).getContent());
        }
    }
    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        BarChart chart;
        public ItemViewHolder(View itemView) {
           super(itemView);
           chart = (BarChart) itemView.findViewById(R.id.barchart);
        }
    }
}
class InfoData {
    InfoData(String name,int data) {
        this.name = name;
        this.v    = data;
    }
    private String name;
    private int    v;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }
}


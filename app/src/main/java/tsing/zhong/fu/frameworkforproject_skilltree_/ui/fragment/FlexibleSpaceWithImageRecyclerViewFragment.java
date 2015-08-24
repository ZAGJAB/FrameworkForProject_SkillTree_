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

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nineoldandroids.view.ViewHelper;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tsing.zhong.fu.frameworkforproject_skilltree_.R;
import tsing.zhong.fu.frameworkforproject_skilltree_.ui.FlexibleSpaceWithImageWithViewPagerTabActivity;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.NetUtil;

public class FlexibleSpaceWithImageRecyclerViewFragment extends FlexibleSpaceWithImageBaseFragment<ObservableRecyclerView> {

    SimpleHeaderRecyclerAdapter adapter;
    String cid = null;
    List<Data> ds;

    public FlexibleSpaceWithImageRecyclerViewFragment() {
        ds = new ArrayList<Data>();
    }
    @Override
    public void setCid(String cid) {
        this.cid = cid;
        NetUtil.get("?c=api&_table=commet&_interface=list&course_id=" + cid, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (!response.getJSONObject("data").equals(false)) {
                        JSONArray jsonarray = response.getJSONObject("data").getJSONArray("items");
                        for (int i = 0; i < jsonarray.length(); ++i) {
                            JSONObject jo = jsonarray.getJSONObject(i);
                            ds.add(new Data(jo.getString("author_id"), jo.getString("time"), jo.getString("commet")));
                        }
                        adapter.notifyDataSetChanged();
                   }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(statusCode, headers, response);
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flexiblespacewithimagerecyclerview, container, false);

        final ObservableRecyclerView recyclerView = (ObservableRecyclerView) view.findViewById(R.id.scroll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);
       final View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.recycler_header, null);
        final int flexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        headerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, flexibleSpaceImageHeight));
        adapter = setMydate(recyclerView, headerView,ds);
        // TouchInterceptionViewGroup should be a parent view other than ViewPager.
        // This is a workaround for the issue #117:
        // https://github.com/ksoichiro/Android-ObservableScrollView/issues/117
        recyclerView.setTouchInterceptionViewGroup((ViewGroup) view.findViewById(R.id.fragment_root));

        // Scroll to the specified offset after layout
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
class Data {
    private String name,date,content;
    public Data(String name,String date,String content) {
        this.name = name;
        this.date = date;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
}
class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<Data> ds;
    public Adapter(List<Data> data) {
        this.ds = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commit_mini, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Data d = ds.get(position);
        holder.uname.setText(d.getName());
        holder.date.setText(d.getDate());
        holder.content.setText(d.getContent());
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView uname;
        TextView date;
        TextView content;
        public ViewHolder(View itemView) {
            super(itemView);
            uname = (TextView) itemView.findViewById(R.id.user);
            date  = (TextView) itemView.findViewById(R.id.date);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}
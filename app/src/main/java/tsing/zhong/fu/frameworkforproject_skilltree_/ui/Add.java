package tsing.zhong.fu.frameworkforproject_skilltree_.ui;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tsing.zhong.fu.frameworkforproject_skilltree_.MyAdapter;
import tsing.zhong.fu.frameworkforproject_skilltree_.R;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.DialogHelper;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.NetUtil;


/**
 * Created by fuzho on 2015/8/11.
 *
 */
public class Add extends AppCompatActivity{
    RecyclerView mRecyclerView;
    LinearLayoutManager layoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    List<String> data;
    String keyw = "";
    MyAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.add_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        data = new ArrayList<>();
        adapter = new MyAdapter(data, Add.this);
        mRecyclerView.setAdapter(adapter);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.add_swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                getProcess();
            }
        });
        getProcess();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.search:
                search();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(){
        DialogHelper.search(Add.this, new MaterialDialog.InputCallback() {
            @Override
            public void onInput(final MaterialDialog materialDialog, CharSequence charSequence) {
                swipeRefreshLayout.setRefreshing(true);
                keyw = charSequence.toString();
                getProcess();
            }
        }).show();
    }
    class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if ("refresh".equals(msg.getData().getString("cmd"))) {
                reFresh();
            }
        }
    }
    private void reFresh() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    private void getProcess(){
        NetUtil.get("?c=api&_table=course&_interface=search&coursename="+keyw, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                data.clear();
                try {
                    if (!response.get("data").equals(false)) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i <jsonArray.length(); i++) {
                            System.out.println(jsonArray.getJSONObject(i).getString("course_id"));
                            data.add(jsonArray.getJSONObject(i).getString("course_id"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                Looper looper = Looper.getMainLooper();
                MainHandler handler = new MainHandler(looper);
                Message msg = handler.obtainMessage(1, 1, 1, "");
                msg.getData().putString("cmd", "refresh");
                handler.sendMessage(msg);
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("error",errorResponse.toString());
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

}

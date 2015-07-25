package tsing.zhong.fu.frameworkforproject_skilltree_.ui;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import android.os.Handler;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import tsing.zhong.fu.frameworkforproject_skilltree_.MyAdapter;
import tsing.zhong.fu.frameworkforproject_skilltree_.MyApplication;
import tsing.zhong.fu.frameworkforproject_skilltree_.R;


/**
 * Created by fuzho on 2015/7/23.
 */
public class MainActivity extends ActionBarActivity {

    public static final List<String> data;

    static {
        data = new ArrayList<String>();
        data.add("课程标题");
        data.add("登山训练");
        data.add("C++ 入门");
        data.add("Haskell 趣学指南");
        data.add("厨师入门指南");
        data.add("吹逼指南");
        data.add("如何获取萌妹子");
        data.add("健康上网技巧");
        data.add("其他");
    }

    LinearLayoutManager layoutManager;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeToolbar();

        MyApplication app = (MyApplication) getApplication();
        app.setMainActivity(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new MyAdapter(data));

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 6000);
            }
        }
        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage("Floating Action Button: Clicked!");
            }
        });
    }
    void sendMessage(String str) {
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
    }
}

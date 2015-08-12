package tsing.zhong.fu.frameworkforproject_skilltree_.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import tsing.zhong.fu.frameworkforproject_skilltree_.MyAdapter;
import tsing.zhong.fu.frameworkforproject_skilltree_.MyApplication;
import tsing.zhong.fu.frameworkforproject_skilltree_.R;
import tsing.zhong.fu.frameworkforproject_skilltree_.model.User;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.DialogHelper;


/**
 * Created by fuzho on 2015/7/23.
 *
 * 程序入口
 */
public class MainActivity extends ActionBarActivity {

    LinearLayoutManager     layoutManager;
    RecyclerView            mRecyclerView;
    SwipeRefreshLayout      swipeRefreshLayout;
    FloatingActionButton    fab;
    TextView                waring;
    FrameLayout             frameLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app.setMainActivity(this);

        setContentView(R.layout.activity_main);
        initializeToolbar();
        if (!u.isOnline()) {
            TextView t = (TextView) findViewById(R.id.main_warning);
            t.setText(R.string.main_activity_login);
        }

            frameLayout = (FrameLayout) findViewById(R.id.main_framelayout);
            waring = (TextView) findViewById(R.id.main_warning);
            mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            MyAdapter adapter = new MyAdapter(app.u.getCourseIdSet());
            adapter.setOnClickListener(this.onClickListener);
            mRecyclerView.setAdapter(adapter);


            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                        @Override
                                                        public void onRefresh() {
                                                            new Handler().postDelayed(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    swipeRefreshLayout.setRefreshing(false);
                                                                }
                                                            }, 3000);
                                                        }
                                                    }
            );
            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.attachToRecyclerView(mRecyclerView);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,Add.class);
                    startActivity(intent);
                }
            });
            refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int res;
        if (u.isOnline()) {
            res = R.menu.main_logout;
        } else {
            res = R.menu.main;
        }
        getMenuInflater().inflate(res, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            //case R.id.action_about:
            //    startActivity(new Intent(MainActivity.this,AboutActivity.class));
            //    break;
            case R.id.action_login:
                //startActivity(new Intent(MainActivity.this,LoginActivity.class));
                DialogHelper.login(this,myCallback).show();
                break;
            //case R.id.action_logout:
            //    u.Logout();
            //    f_refresh();
            //    break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }

    void sendMessage(String str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
    }
    MaterialDialog.ButtonCallback myCallback = new MaterialDialog.ButtonCallback() {
        @Override
        public void onPositive(MaterialDialog dialog) {
            super.onPositive(dialog);
            LinearLayout v = (LinearLayout)dialog.getCustomView();
            EditText user = null;
            EditText pwd  = null;
            if (v != null) {
                user = (EditText) v.findViewById(R.id.mini_login_user);
                pwd  = (EditText) v.findViewById(R.id.mini_login_pwd);
            }
            if (app.u.Login(user.getText().toString(),pwd.getText().toString())==0) {
                sendMessage("登陆成功");
                swipeRefreshLayout.setRefreshing(true);
                f_refresh();
            } else {
                sendMessage("账号密码错误");
            }
        }
    };


    @Override
    void refresh() {
        super.refresh();

        if (!u.isOnline()) {
            waring.setText(R.string.main_activity_login);
            fab.hide(true);
        } else {
            waring.setText(R.string.main_activity_add);
            fab.show(true);
        }
       if (swipeRefreshLayout!=null) swipeRefreshLayout.setRefreshing(false);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,
                    FlexibleSpaceWithImageWithViewPagerTab2Activity.class));
        }
    };
}

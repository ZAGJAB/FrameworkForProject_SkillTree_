package tsing.zhong.fu.frameworkforproject_skilltree_.ui;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.*;
import android.os.Message;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.melnykov.fab.FloatingActionButton;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tsing.zhong.fu.frameworkforproject_skilltree_.MyAdapter;
import tsing.zhong.fu.frameworkforproject_skilltree_.MyApplication;
import tsing.zhong.fu.frameworkforproject_skilltree_.R;
import tsing.zhong.fu.frameworkforproject_skilltree_.model.User;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.DialogHelper;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.NetUtil;


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
    MaterialDialog.Builder  processBuilder;
    JSONArray               array;
    String                  pass;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app.setMainActivity(this);
        setContentView(R.layout.activity_main);
        initializeToolbar();

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

    void sendToastMessage(String str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
    }
    MaterialDialog.ButtonCallback myCallback = new MaterialDialog.ButtonCallback() {
        @Override
        public void onPositive(final MaterialDialog dialog) {
            if (processBuilder == null) {
                processBuilder = DialogHelper.process(MainActivity.this);
            }
            processBuilder.show();
            super.onPositive(dialog);
            LinearLayout v = (LinearLayout)dialog.getCustomView();
            EditText user = null;
            EditText pwd  = null;
            if (v != null) {
                user = (EditText) v.findViewById(R.id.mini_login_user);
                pwd  = (EditText) v.findViewById(R.id.mini_login_pwd);
            }
            pass = pwd.getText().toString();
            try {
                app.u.Login(user.getText().toString(),
                            pwd.getText().toString(),
                            new JsonHttpResponseHandler(){
                                @Override
                                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                    //processBuilder = null;
                                    processBuilder.autoDismiss(true);
                                    sendToastMessage("通信失败");
                                    f_refresh();
                                    super.onFailure(statusCode, headers, throwable, errorResponse);
                                }

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    try {
                                        if (response.get("err_msg").equals("success")) {
                                            if(response.get("data").equals(false)) {
                                                sendToastMessage("账号密码不匹配");
                                                f_refresh();
                                            } else {
                                                sendToastMessage("登陆成功");
                                                array = (JSONArray) ((JSONObject)response.get("data")).get("items");
                                                JSONObject json = array.getJSONObject(0);
                                                try {
                                                    u.setAccount((String) json.get("account"));
                                                    u.setId((String) json.get("user_id"));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                NetUtil.get("?c=api&_table=user&_interface=get_token&account=" + u.getAccount() + "&password=" + pass, null, new JsonHttpResponseHandler() {
                                                    @Override
                                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                        try {
                                                            JSONObject jsondataToken = (JSONObject) response.get("data");
                                                            u.setUtoken ((String) jsondataToken.get("token"));
                                                            NetUtil.get("?c=api&_table=user_data&_interface=list&user_id=" + u.getId() + "&token=" + u.getUtoken(), null, new JsonHttpResponseHandler() {
                                                                @Override
                                                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                                    try {
                                                                        if (!response.get("data").equals(false)) {
                                                                            JSONArray data = ((JSONObject) response.get("data")).getJSONArray("items");
                                                                            System.out.println(data.toString());
                                                                            JSONObject jdata = data.getJSONObject(0);
                                                                            u.setUname(jdata.getString("nickname"));
                                                                            u.setSig(jdata.getString("sig"));
                                                                        } else {
                                                                            NetUtil.post("http://apiapiapi.sinaapp.com/?c=api&_table=user_data&_interface=insert&nickname="+u.getAccount()+"&sig=写点什么吧!"+"&user_id="+u.getId()+"&token="+u.getUtoken(),null,new JsonHttpResponseHandler(){
                                                                            });
                                                                            u.setUname(u.getAccount());
                                                                            u.setSig("写点什么吧！");
                                                                        }
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    Looper looper = Looper.getMainLooper();
                                                                    Main_Handler handler = new Main_Handler(looper);
                                                                    Message msg = handler.obtainMessage(1,1,1,"");
                                                                    msg.getData().putString("cmd","refresh");
                                                                    u.setOnline(true);
                                                                    handler.sendMessage(msg);
                                                                    super.onSuccess(statusCode, headers, response);
                                                                }
                                                            });
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        super.onSuccess(statusCode, headers, response);
                                                    }
                                                });

                                            }
                                            //sendToastMessage(response.toString());
                                        } else {
                                                sendToastMessage("用户名称未填");
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    //f_refresh();
                                    //super.onSuccess(statusCode, headers, response);
                                }
                            });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNegative(MaterialDialog dialog) {
            super.onNegative(dialog);
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
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
        if (u.getCourseIdSet().size() !=0) {
            waring.setVisibility(View.INVISIBLE);
        }
       if (swipeRefreshLayout!=null) swipeRefreshLayout.setRefreshing(false);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,
                    FlexibleSpaceWithImageWithViewPagerTabActivity.class));
        }
    };

    class Main_Handler extends Handler {
        public Main_Handler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ("refresh".equals(msg.getData().getString("cmd"))) {
                f_refresh();
            }
        }
    }

}

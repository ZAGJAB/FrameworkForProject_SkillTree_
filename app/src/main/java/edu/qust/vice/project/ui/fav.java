package edu.qust.vice.project.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.qust.vice.project.MyApplication;
import edu.qust.vice.project.utils.NetUtil;
import tsing.zhong.fu.frameworkforproject_skilltree_.R;
import edu.qust.vice.project.model.User;

/**
 * Created by fuzho on 2015/8/26.
 *
 */
public class fav extends AppCompatActivity {
    LinearLayoutManager     layoutManager;
    RecyclerView            mRecyclerView;
    MyAdapter               adapter;
    ArrayList<Data>   list;
    String content = "";
    MaterialDialog dialog;
    User u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        u = ((MyApplication)getApplication()).u;
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        list = new ArrayList<Data>();
        mRecyclerView = (RecyclerView) findViewById(R.id.fav_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(list,fav.this);
        mRecyclerView.setAdapter(adapter);

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }
        Button button = (Button) findViewById(R.id.newd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new MaterialDialog.Builder(fav.this)
                        .title("创建新课程")
                        .customView(R.layout.add_new_course, false)
                        .positiveText("创建")
                        .negativeText("取消")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                //TextView text = t;
                                View view = dialog.getCustomView();
                                TextView t = (TextView) view.findViewById(R.id.name);
                                int tot = Integer.parseInt(u.getV1()) +
                                        Integer.parseInt(u.getV2()) +
                                        Integer.parseInt(u.getV3());
                                int v1 = (int)(Integer.parseInt(u.getV1())*1.0 / tot * 100);
                                int v2 = (int)(Integer.parseInt(u.getV2())*1.0 / tot * 100);
                                int v3 = (int)(Integer.parseInt(u.getV3())*1.0 / tot * 100);
                                Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
                                NetUtil.get("?c=api&_table=course&_interface=insert" +
                                                "&course_name=" + t.getText()
                                                + "&course_type_id=" + spinner.getSelectedItemPosition()
                                                + "&publisher=" + u.getId()
                                                + "&effort=" + v1
                                                + "&talent=" + v2
                                                + "&excited=" + v3, null, new JsonHttpResponseHandler() {
                                            @Override
                                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                                Looper looper = Looper.getMainLooper();
                                                MainHandler handler = new MainHandler(looper);
                                                Message msg = handler.obtainMessage(1, 1, 1, "");
                                                msg.getData().putString("cmd", "getprocess");
                                                handler.sendMessage(msg);
                                                super.onFailure(statusCode, headers, responseString, throwable);
                                            }

                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                                super.onSuccess(statusCode, headers, responseString);
                                            }
                                        }
                                );
                                super.onPositive(dialog);
                            }
                        })
                        .show();




            }
        });
    }
    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            if (((MyApplication)getApplication()).u.isOnline()) {
                content = sharedText;
                getprocess();
            } else {
                Toast.makeText(fav.this,"请先登录",Toast.LENGTH_LONG).show();
                startActivity(new Intent(fav.this, MainActivity.class));
            }

        }
    }

    void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
        }
    }

    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
        }
    }
 class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private ArrayList<Data> data;
        private Activity mActivity;
        private User u;
        public MyAdapter(ArrayList<Data> data,Activity mActivity) {
            this.data = data;
            this.mActivity = mActivity;
            u = ((MyApplication)mActivity.getApplication()).u;
        }

        private View.OnClickListener onClickListener = null;
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            // 加载Item的布局.布局中用到的真正的CardView.
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wapper_item, viewGroup, false);
            // ViewHolder参数一定要是Item的Root节点.
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.text.setText(data.get(i).getV());
            final String cid = data.get(i).getID();
            if (content != null) {
                viewHolder.text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(fav.this, "添加成功", Toast.LENGTH_LONG).show();
                        Looper looper = Looper.getMainLooper();
                        MainHandler handler = new MainHandler(looper);
                        Message msg = handler.obtainMessage(1, 1, 1, "");
                        msg.getData().putString("cmd", "finish");
                        handler.sendMessage(msg);

                        NetUtil.post("?c=api&_table=course_hour&_interface=insert&course_id=" + cid + "&course_hour_content=" + content
                                , null
                                , new JsonHttpResponseHandler());
                    }
                });
            }
            viewHolder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NetUtil.get("?c=api&_table=course&_interface=remove&course_id="+cid
                            , null
                            , new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Looper looper = Looper.getMainLooper();
                            MainHandler handler = new MainHandler(looper);
                            Message msg = handler.obtainMessage(1, 1, 1, "");
                            msg.getData().putString("cmd", "getprocess");
                            handler.sendMessage(msg);
                            Toast.makeText(mActivity,"删除成功",Toast.LENGTH_LONG).show();
                            super.onSuccess(statusCode, headers, response);
                        }
                    });
                }
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            View main;
            TextView text;
            TextView del;
            public ViewHolder(View itemView) {
                // super这个参数一定要注意,必须为Item的根节点.否则会出现莫名的FC.
                super(itemView);
                main = itemView;
                text = (TextView) itemView.findViewById(R.id.main_text);
                del  = (TextView) itemView.findViewById(R.id.del_text);
            }
        }
    }
    void getprocess() {
        list.clear();
        NetUtil.get("?c=api&_table=course&_interface=getitem&uid="+((MyApplication)getApplication()).u.getId()
                ,null
                , new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (!response.get("data").equals(false)) {
                        JSONArray jarray = response.getJSONArray("data");
                        for(int i = 0; i<jarray.length();++i)
                        {
                            list.add(new Data(jarray.getJSONObject(i).getString("course_name"), jarray.getJSONObject(i).getString("course_id")));
                        }
                        adapter.notifyDataSetChanged();
                    }
                    super.onSuccess(statusCode, headers, response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    class Data {
        String v,id;
        Data(String v,String id) {
            this.v = v;
            this.id = id;
        }

        public void setID(String d) {
            this.id = d;
        }

        public void setV(String v) {
            this.v = v;
        }

        public String getID() {
            return id;
        }

        public String getV() {
            return v;
        }
    }
    class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if ("getprocess".equals(msg.getData().getString("cmd"))) {
                getprocess();
            }
            if ("getprocess".equals(msg.getData().getString("finish"))) {
                if (dialog != null) dialog.dismiss();
                finish();
            }
        }
    }
}

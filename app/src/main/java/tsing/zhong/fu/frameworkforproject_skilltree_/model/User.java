package tsing.zhong.fu.frameworkforproject_skilltree_.model;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tsing.zhong.fu.frameworkforproject_skilltree_.ui.MainActivity;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.NetUtil;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.Util;

/**
 * Created by fuzho on 2015/7/23.
 *
 * 用户的model
 * 本地数据库只记录是否已经登陆
 *
 */
public class User {

    private  boolean online = false;
    private  String  token;
    private  String  Uname,Sig,id,account;
    private  List<String> courseIdSet = null;

    public static User rt = null;
    public static User getInstance() {
        if (rt == null) rt = new User();
        return rt;
    }
    private void sync_local() {
        //todo 和本地数据库同步数据 内存 -> 数据库
        Log.i("fzq","同步 内存 -> 数据库");
    }
    private void sync_net() {

    }
    private User() {
        //todo 从数据库读取到这个类 数据库 -> 内存
        online = false;
        if (online) {
            Uname = "fuzhongqing";
            Sig   = "hehehe";
        }
        courseIdSet = new ArrayList<>();
    };

    public void Login(String u,String p,JsonHttpResponseHandler handler) throws IOException {

        NetUtil.get("?c=api&_table=user&_interface=list&account="+u+"&password="+ Util.md5(p),null,handler);
    }
    public void setDetail(JSONObject json,String pass) {
        online = true;
        try {
            account = (String) json.get("account");
            id      = (String) json.get("uid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetUtil.get("?c=api&_table=user&_interface=get_token&account="+account+"&password="+pass,null,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    token = (String)((JSONObject)response.get("data")).get("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                super.onSuccess(statusCode, headers, response);
            }
        });
    }
    public void Logout() {
        online = false;
        courseIdSet.clear();
    }

    /*查询部分*/
    public boolean isOnline() {
        return online;
    }
    public String  getName() {
        return Uname;
    }
    public String  getSig() {
        return Sig;
    }
    public List<String> getCourseIdSet() {
        return courseIdSet;
    }

}

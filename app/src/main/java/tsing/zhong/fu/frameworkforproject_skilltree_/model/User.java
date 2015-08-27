package tsing.zhong.fu.frameworkforproject_skilltree_.model;

import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
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
import tsing.zhong.fu.frameworkforproject_skilltree_.ui.Message;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.NetUtil;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.Util;

import static tsing.zhong.fu.frameworkforproject_skilltree_.ui.MainActivity.*;

/**
 * Created by fuzho on 2015/7/23.
 *
 * 用户的model
 * 本地数据库只记录是否已经登陆
 *
 */
public class User {

    private  boolean online = false;
    private  String  Uname,Sig,id,account,Utoken,v1,v2,v3;
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

        NetUtil.get("?c=api&_table=user&_interface=list&account=" + u + "&password=" + Util.md5(p), null, handler);
    }
    public void Logout() {
        online = false;
        courseIdSet.clear();
    }

    /*查询部分*/
    public boolean isOnline() {
        return online;
    }
    public List<String> getCourseIdSet() {
        return courseIdSet;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUtoken() {
        return Utoken;
    }

    public void setUtoken(String utoken) {
        Utoken = utoken;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getSig() {
        return Sig;
    }

    public void setSig(String sig) {
        Sig = sig;
    }

    public void setOnline(boolean b) {
        online = b;
    }

    public void addCourese(String id) {
        courseIdSet.add(id);
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    public String getV3() {
        return v3;
    }

    public void setV3(String v3) {
        this.v3 = v3;
    }
}

package tsing.zhong.fu.frameworkforproject_skilltree_.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by fuzho on 2015/7/23.
 *
 * 用户的model
 * 本地数据库只记录是否已经登陆
 *
 */
public class User {

    private  boolean online = false;
    private  String  Uname,Sig;
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

    public int Login(String u,String p) {
        if (u.equals("fzq")&&p.equals("zzz")) {
            //同步到数据库
            Uname = "付忠庆";
            Sig   ="呵呵呵呵";
            online = true;
            courseIdSet.add("0001");
            courseIdSet.add("0003");
            return 0;
        }
        return -1;
    };
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

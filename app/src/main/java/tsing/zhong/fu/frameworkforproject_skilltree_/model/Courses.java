package tsing.zhong.fu.frameworkforproject_skilltree_.model;

/**
 * Created by fuzho on 2015/7/23.
 *
 * 课程model类
 *
 * TODO: 添加单击事件
 */

import android.content.Context;

import com.loopj.android.http.HttpGet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import tsing.zhong.fu.frameworkforproject_skilltree_.MyAdapter;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.NetUtil;

public class Courses {
    final String[] kind = {
        "人文","科学","数学","计算机"
    };
    String title,title2,type;
    String picName;
    String id;
    public boolean isInList;
    public Courses(String id, final MyAdapter sz, final int pos) {
        isInList = false;
        //todo 根据id同步网络数据
        NetUtil.get("?c=api&_table=course&_interface=list&course_id="+id,null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (!response.get("data").equals(false)) {
                        JSONObject json = response.getJSONObject("data").getJSONArray("items").getJSONObject(0);
                        title = json.getString("course_name");
                        type  = kind[json.getInt("course_type_id")];

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sz.notifyItemChanged(pos);
                super.onSuccess(statusCode, headers, response);
            }
        });
        title = "正在加载中...";
        title2 = "";
        this.id = id;
        picName = Integer.parseInt(id) % 7 +"";
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getPicName() {
        return picName;
    }

    public String getType() {
        return type;
    }

    public String getTitle2() { return title2; }
}

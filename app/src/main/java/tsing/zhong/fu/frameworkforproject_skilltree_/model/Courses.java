package tsing.zhong.fu.frameworkforproject_skilltree_.model;

/**
 * Created by fuzho on 2015/7/23.
 *
 * 课程model类
 *
 * TODO: 添加单击事件
 */

import android.content.Context;
import java.util.*;

public class Courses {
    String title;
    String picName;
    String id;
    public Courses(String id) {
        //todo 根据id同步网络数据
        title = "id 为" + id + "的课程";
        this.id = id;
        picName = new Random().nextInt(7) + "";
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
}

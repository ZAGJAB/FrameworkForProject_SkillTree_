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
    public Courses(String title, String id, String picName) {
        this.title = title;
        this.picName = picName;
        this.id = id;
    }
    public Courses(String title, String id) {
        this.title = title;
        this.picName = picName;
        this.id = "sys" + (new Random(5).nextInt());
    }

    public String getTitle(){
        return this.title;
    }

    public int getImageResourceId( Context context )
    {
        try
        {
            return context.getResources().getIdentifier(this.picName, "drawable", context.getPackageName());

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }
}

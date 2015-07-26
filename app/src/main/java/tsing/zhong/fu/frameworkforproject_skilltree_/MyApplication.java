package tsing.zhong.fu.frameworkforproject_skilltree_;

import android.app.Application;

import tsing.zhong.fu.frameworkforproject_skilltree_.ui.MainActivity;


/**
 * Created by fuzho on 2015/7/25.
 *
 *
 *
 */
public class MyApplication extends Application {

    public static MainActivity mainActivity;
    @Override
    public void onCreate() {
        super.onCreate();
        
    }

    public void setMainActivity(MainActivity m) {
        mainActivity = m;
    }
    public MainActivity getMainActivity(){
        return mainActivity;
    }

}

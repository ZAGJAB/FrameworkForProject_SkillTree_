package edu.qust.vice.project;

import android.app.Application;

import edu.qust.vice.project.model.User;
import edu.qust.vice.project.ui.MainActivity;


/**
 * Created by fuzho on 2015/7/25.
 *
 *
 *
 */
public class MyApplication extends Application {

    public static MainActivity mainActivity;
    public static User u = User.getInstance();
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

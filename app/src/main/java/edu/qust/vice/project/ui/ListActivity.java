package edu.qust.vice.project.ui;

import android.os.Bundle;

import tsing.zhong.fu.frameworkforproject_skilltree_.R;

/**
 * Created by fuzho on 2015/7/23.
 * 测试List的activity
 */
public class ListActivity extends ActionBarActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initializeToolbar();

    }


}

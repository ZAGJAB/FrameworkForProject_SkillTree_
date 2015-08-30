package edu.qust.vice.project.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import tsing.zhong.fu.frameworkforproject_skilltree_.R;

/**
 * Created by fuzho on 2015/7/26.
 *
 */
public abstract class BaseActionbarActivity extends AppCompatActivity{

    private Toolbar mToolBar;
    private boolean mToolbarInitialized = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mToolbarInitialized) {
            throw new IllegalStateException("ToolBar 未初始化");
        }
    }

    protected void initializeToolbar() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolBar == null) {
            throw new IllegalStateException("主layout 必须包含 ToolBar");
        }

        mToolbarInitialized = true;
    }
}

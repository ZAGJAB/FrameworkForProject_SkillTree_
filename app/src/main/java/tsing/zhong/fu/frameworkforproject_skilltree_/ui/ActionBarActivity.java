
package tsing.zhong.fu.frameworkforproject_skilltree_.ui;

import android.app.ActivityOptions;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import tsing.zhong.fu.frameworkforproject_skilltree_.MyApplication;
import tsing.zhong.fu.frameworkforproject_skilltree_.R;
import tsing.zhong.fu.frameworkforproject_skilltree_.model.User;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.Loger;
import tsing.zhong.fu.frameworkforproject_skilltree_.utils.ResourceHelper;

/**
 * Created by fuzho on 2015/7/23.
 *
 * 基础activity 提供toolbar,DrawerList和仿MD
 *
 * 其子类在onCreate方法中必须调用  {@link #initializeToolbar()} ,并且是在 setContentView()之后
 * 子类绑定的xml必须包含:
 * 一个 {@link android.support.v7.widget.Toolbar} , id: 'toolbar',
 * 一个 {@link android.support.v4.widget.DrawerLayout} , id: 'drawerLayout'
 * 一个 {@link ListView} , id: 'drawerList'.
 */

public abstract class ActionBarActivity extends AppCompatActivity {

    private static final String TAG = Loger.makeLogTag(ActionBarActivity.class);

    private static final int DELAY_MILLIS = 1000;

    User             u;
    MyApplication    app;
    TextView         settingTextView;
    TextView         logoutTextView;
    TextView         aboutTextView;
    TextView         username;
    TextView         usersig;
    RoundedImageView headpic;

    //顶部Bar
    private Toolbar mToolbar;
    //汉堡包菜单图标
    private ActionBarDrawerToggle mDrawerToggle;
    //侧栏
    private DrawerLayout mDrawerLayout;
    //侧栏列表
    private ListView mDrawerList;
    //侧栏事件

    private DrawerMenuContents mDrawerMenuContents;

    private boolean mToolbarInitialized;

    private int mItemToOpenWhenDrawerCloses = -1;


    private final DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerClosed(View drawerView) {
            if (mDrawerToggle != null) mDrawerToggle.onDrawerClosed(drawerView);
            int position = mItemToOpenWhenDrawerCloses;
            if (position >= 0) {
                Bundle extras = ActivityOptions.makeCustomAnimation(
                    ActionBarActivity.this, R.anim.fade_in, R.anim.fade_out).toBundle();

                Class activityClass = mDrawerMenuContents.getActivity(position);
                startActivity(new Intent(ActionBarActivity.this, activityClass), extras);
                finish();
            }
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            if (mDrawerToggle != null) mDrawerToggle.onDrawerStateChanged(newState);
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            if (mDrawerToggle != null) mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            if (mDrawerToggle != null) mDrawerToggle.onDrawerOpened(drawerView);
        }
    };

    private final FragmentManager.OnBackStackChangedListener mBackStackChangedListener =
        new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                updateDrawerToggle();
            }
        };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyApplication) getApplication();
        u = app.u;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("fzq", "onstart");
        //添加是否调用的劫持判断
        if (!mToolbarInitialized) {
            throw new IllegalStateException("请在onCreate方法中调用initializeToolbar()");
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // 同步图标状态
        getFragmentManager().addOnBackStackChangedListener(mBackStackChangedListener);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle != null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getFragmentManager().removeOnBackStackChangedListener(mBackStackChangedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // If not handled by drawerToggle, home needs to be handled by returning to previous
        if (item != null && item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // 如果菜单打开 那么返回键是收回菜单
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        // 其他的情况的话就是来到栈底下的fragment
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            // 如果栈也是空的话 那就是activity了
            super.onBackPressed();
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mToolbar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        mToolbar.setTitle(titleId);
    }

    protected void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) {
            throw new IllegalStateException("XML里必须包含一个Toolbar");
        }
        mToolbar.inflateMenu(R.menu.main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (mDrawerLayout != null) {
            mDrawerList = (ListView) findViewById(R.id.drawer_list);
            if (mDrawerList == null) {
                throw new IllegalStateException("XML里必须包含一个drawer_List");
            }

            //添加事件
            username = (TextView) findViewById(R.id.user_name);
            usersig  = (TextView) findViewById(R.id.user_sig);
            headpic  = (RoundedImageView) findViewById(R.id.headpic);
            final MyApplication app = (MyApplication)getApplication();


            settingTextView = (TextView) findViewById(R.id.drawlist_setting);
            settingTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                }
            });

            aboutTextView = (TextView) findViewById(R.id.drawlist_about);
            aboutTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                }
            });

            logoutTextView = (TextView) findViewById(R.id.drawlist_logout);
            logoutTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    app.u.Logout();
                    f_refresh();
                }
            });

            // 加一个汉堡包图标
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.open_content_drawer, R.string.close_content_drawer);
            mDrawerLayout.setDrawerListener(mDrawerListener);
            mDrawerLayout.setStatusBarBackgroundColor(
                ResourceHelper.getThemeColor(this, R.attr.colorPrimary, android.R.color.black));
            populateDrawerItems();
            setSupportActionBar(mToolbar);
            updateDrawerToggle();
        } else {

            setSupportActionBar(mToolbar);
        }

        mToolbarInitialized = true;
    }

    private void populateDrawerItems() {
        mDrawerMenuContents = new DrawerMenuContents(this);
        final int selectedPosition = mDrawerMenuContents.getPosition(this.getClass());
        final int unselectedColor = Color.WHITE;
        final int selectedColor = getResources().getColor(R.color.drawer_item_selected_background);
        SimpleAdapter adapter = new SimpleAdapter(this, mDrawerMenuContents.getItems(),
                R.layout.drawer_list_item,
                new String[]{DrawerMenuContents.FIELD_TITLE, DrawerMenuContents.FIELD_ICON},
                new int[]{R.id.drawer_item_title, R.id.drawer_item_icon}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                int color = unselectedColor;
                if (position == selectedPosition) {
                    color = selectedColor;
                }
                view.setBackgroundColor(color);
                return view;
            }
        };

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != selectedPosition) {
                    view.setBackgroundColor(getResources().getColor(
                            R.color.drawer_item_selected_background));
                    mItemToOpenWhenDrawerCloses = position;
                }
                mDrawerLayout.closeDrawers();
            }
        });
        mDrawerList.setAdapter(adapter);
    }

    protected void updateDrawerToggle() {
        if (mDrawerToggle == null) {
            return;
        }
        boolean isRoot = getFragmentManager().getBackStackEntryCount() == 0;
        mDrawerToggle.setDrawerIndicatorEnabled(isRoot);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(!isRoot);
            getSupportActionBar().setDisplayHomeAsUpEnabled(!isRoot);
            getSupportActionBar().setHomeButtonEnabled(!isRoot);
        }
        if (isRoot) {
            mDrawerToggle.syncState();
        }
    }
    void f_refresh() {
        Intent ref = getIntent();
        ref.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(ref);
        overridePendingTransition(0, 0);
    };
    void refresh() {
        if (u.isOnline()){
            logoutTextView.setEnabled(true);
            username.setText(u.getUname());
            usersig.setText(u.getSig());
        } else {
            logoutTextView.setEnabled(false);
            username.setText(R.string.warning_login);
            usersig.setText(R.string.sig);
        }
    }
    /**
     * Shows the Cast First Time User experience to the user (an overlay that explains what is
     * the Cast icon)
     *
    private void showFtu() {
        Menu menu = mToolbar.getMenu();
        View view = menu.findItem(R.id.media_route_menu_item).getActionView();
        if (view != null && view instanceof MediaRouteButton) {
            new ShowcaseView.Builder(this)
                    .setTarget(new ViewTarget(view))
                    .setContentTitle(R.string.touch_to_cast)
                    .hideOnTouchOutside()
                    .build();
        }
    }
     */
}

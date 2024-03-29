package edu.qust.vice.project.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import edu.qust.vice.project.ui.fragment.NewFav;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import tsing.zhong.fu.frameworkforproject_skilltree_.R;
import edu.qust.vice.project.ui.fragment.FragmentFav;

/**
 * Created by fuzho on 2015/8/11.
 *
 */
public class Message extends ActionBarActivity implements MaterialTabListener {

    MaterialTabHost tabHost;
    ViewPager pager;
    ViewPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        initializeToolbar();


        tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
        pager = (ViewPager) this.findViewById(R.id.pager );

        // init view pager
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);

            }
        });

        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            );

        }
        refresh();
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        pager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        public Fragment getItem(int num) {
            if (num == 0) {
                return new FragmentFav();
            } else {
                return new NewFav();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String[] s = new String[2];
            s[0] = "我的课程";
            s[1] = "创建课程";
            return s[position];
        }

    }

}

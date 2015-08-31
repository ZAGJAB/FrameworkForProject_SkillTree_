/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.qust.vice.project.ui;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tsing.zhong.fu.frameworkforproject_skilltree_.R;

public class DrawerMenuContents {
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_ICON = "icon";

    private final ArrayList<Map<String, ?>> items;
    private final Class[] activities;

    public DrawerMenuContents(Context ctx) {
        activities = new Class[4];
        items = new ArrayList<>(4);

        activities[0] = MainActivity.class;
        items.add(populateDrawerItem("课程进度",
                R.drawable.ic_list_black_48dp));

        activities[1] = Friends.class;
        items.add(populateDrawerItem("我的好友",
                R.drawable.ic_star_rate_black_18dp));


        activities[2] = About.class;
        items.add(populateDrawerItem("我的信息",
                R.drawable.ic_star_rate_black_18dp));

        activities[3] = Message.class;
        items.add(populateDrawerItem("我的收藏",
                R.drawable.ic_star_rate_black_18dp));

    }

    public List<Map<String, ?>> getItems() {
        return items;
    }

    public Class getActivity(int position) {
        return activities[position];
    }

    public int getPosition(Class activityClass) {
        for (int i=0; i<activities.length; i++) {
            if (activities[i].equals(activityClass)) {
                return i;
            }
        }
        return -1;
    }

    private Map<String, ?> populateDrawerItem(String title, int icon) {
        HashMap<String, Object> item = new HashMap<>();
        item.put(FIELD_TITLE, title);
        item.put(FIELD_ICON, icon);
        return item;
    }
}

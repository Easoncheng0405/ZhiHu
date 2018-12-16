/*
 * Copyright [2018] [chengjie.jlu@qq.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jlu.chengjie.zhihu.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.jlu.chengjie.zhihu.R;
import com.jlu.chengjie.zhihu.adapter.TabAdapter;
import com.jlu.chengjie.zhihu.fragment.HomeFragment;
import com.jlu.chengjie.zhihu.fragment.MyFragment;
import com.jlu.chengjie.zhihu.fragment.WatchFragment;
import com.jlu.chengjie.zhihu.util.ZLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 *@Author chengjie
 *@Date 2018-12-14
 *@Email chengjie.jlu@qq.com
 */

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private final String TAG = "MainActivity";

    private final int HOME = 0;
    private final int EYE = 1;
    private final int MY = 2;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initTabLayout();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        ZLog.d(TAG, "onTabSelected tab: " + tab.getPosition());
        switch (tab.getPosition()) {
            case HOME:
                tab.setIcon(R.drawable.tab_home_active);
                break;
            case EYE:
                tab.setIcon(R.drawable.tab_eye_active);
                break;
            case MY:
                tab.setIcon(R.drawable.tab_my_active);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        ZLog.d(TAG, "onTabUnselected tab: " + tab.getPosition());
        switch (tab.getPosition()) {
            case HOME:
                tab.setIcon(R.drawable.tab_home);
                break;
            case EYE:
                tab.setIcon(R.drawable.tab_eye);
                break;
            case MY:
                tab.setIcon(R.drawable.tab_my);
                break;
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        ZLog.d(TAG, "onTabReselected tab: " + tab.getPosition());
    }

    /**
     * when initialize tab item icon,no need to initialize home tab
     * because it will be selected when activity create,then it will be
     * initialize by {@link #onTabSelected(TabLayout.Tab)}
     */
    private void initTabLayout() {
        ZLog.d(TAG, "start to initialize TabLayout.");
        tabLayout.addOnTabSelectedListener(this);
        HomeFragment homeFragment = new HomeFragment();
        WatchFragment watchFragment = new WatchFragment();
        MyFragment myFragment = new MyFragment();
        List<Fragment> fragments = new ArrayList<Fragment>() {
            {
                add(homeFragment);
                add(watchFragment);
                add(myFragment);
            }
        };

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), fragments);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);

        try {
            Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.tab_eye);
            Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.tab_my);
        } catch (NullPointerException e) {
            ZLog.e(TAG, "initialize tab item icon exception: ", e);
        }
    }
}

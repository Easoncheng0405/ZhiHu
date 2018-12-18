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

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.jlu.chengjie.zhihu.R;
import com.jlu.chengjie.zhihu.adapter.PersonalAdapter;
import com.jlu.chengjie.zhihu.adapter.QuestionsAdapter;
import com.jlu.chengjie.zhihu.model.SimpleQuestion;
import com.jlu.chengjie.zhihu.util.PixelUtil;
import com.jlu.chengjie.zhihu.view.PersonalViewPager;

import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends AppCompatActivity implements NestedScrollView.OnScrollChangeListener {

    private final String TAG = "PersonalActivity";

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    PersonalViewPager viewPager;

    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;

    @BindView(R.id.title)
    RelativeLayout titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        scrollView.setOnScrollChangeListener(this);
        RecyclerView recyclerView1 = new RecyclerView(this);
        RecyclerView recyclerView2 = new RecyclerView(this);
        RecyclerView recyclerView3 = new RecyclerView(this);

        SimpleQuestion question = new SimpleQuestion();
        question.setTitle("网络流行语是否被禁止进入高考语文作文？为什么？");
        question.setContent("王郑宁：关于阅卷老师的年龄，有不少人告诉我他们城市是研究生批高考作文。请恕我和孤陋寡闻。因为我参与过，了解过的情况，魔都的语文作文阅卷一直都是：普通评卷...");
        question.setInfo("7309 赞同 · 374评论");

        List<SimpleQuestion> list = new ArrayList<SimpleQuestion>() {
            {
                add(question);
                add(question);
                add(question);
                add(question);
                add(question);
                add(question);
            }
        };

        QuestionsAdapter adapter1 = new QuestionsAdapter(this, list);
        QuestionsAdapter adapter2 = new QuestionsAdapter(this, list);
        QuestionsAdapter adapter3 = new QuestionsAdapter(this, list);

        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView1.setAdapter(adapter1);

        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView2.setAdapter(adapter2);

        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        recyclerView3.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView3.setAdapter(adapter3);

        List<RecyclerView> recyclerViews = new ArrayList<>();

        recyclerView1.setFocusableInTouchMode(false);
        recyclerView2.setFocusableInTouchMode(false);
        recyclerView3.setFocusableInTouchMode(false);

        recyclerViews.add(recyclerView1);
        recyclerViews.add(recyclerView2);
        recyclerViews.add(recyclerView3);

        PersonalAdapter personalAdapter = new PersonalAdapter(new String[]{"动态", "提问", "回答"}, recyclerViews);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(personalAdapter);
    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int height = PixelUtil.px2dp(this, scrollY);
        if (height > 300)
            return;
        int alpha = (int) (255 * (height * 1.0 / 200));
        alpha = alpha > 255 ? 255 : alpha;
        int color = Color.argb(alpha, 150, 150, 150);
        titleBar.setBackgroundColor(color);
    }

    @OnClick(R.id.back)
    void backPress(){
        onBackPressed();
    }
}

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

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jlu.chengjie.zhihu.R;
import com.jlu.chengjie.zhihu.adapter.QuestionsAdapter;
import com.jlu.chengjie.zhihu.model.SimpleQuestion;

import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends AppCompatActivity {

    @BindView(R.id.questions)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);


        SimpleQuestion question = new SimpleQuestion();
        question.setTitle("网络流行语是否被禁止进入高考语文作文？为什么？");
        question.setContent("王郑宁：关于阅卷老师的年龄，有不少人告诉我他们城市是研究生批高考作文。请恕我和孤陋寡闻。因为我参与过，了解过的情况，魔都的语文作文阅卷一直都是：普通评卷...");
        question.setInfo("7309 赞同 · 374 评论");

        List<SimpleQuestion> list = new ArrayList<SimpleQuestion>() {
            {
                add(question);
                add(question);
                add(question);
                add(question);
                add(question);
                add(question);
                add(question);
                add(question);
                add(question);
                add(question);
                add(question);
                add(question);
            }
        };

        QuestionsAdapter adapter = new QuestionsAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

    }
}

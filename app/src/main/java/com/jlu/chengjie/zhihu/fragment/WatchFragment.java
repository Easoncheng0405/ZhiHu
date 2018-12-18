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

package com.jlu.chengjie.zhihu.fragment;

/*
 *@Author chengjie
 *@Date 2018-12-15
 *@Email chengjie.jlu@qq.com
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.jlu.chengjie.zhihu.R;
import com.jlu.chengjie.zhihu.adapter.FocusAdapter;
import com.jlu.chengjie.zhihu.model.FocusPeople;

import java.util.ArrayList;
import java.util.List;

public class WatchFragment extends Fragment {

    @BindView(R.id.focus_people)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.watch_fragment, container, false);
        ButterKnife.bind(this, view);

        FocusPeople people = new FocusPeople();
        people.setPeople("知乎刘看山 关注了问题 · 3小时 前");
        people.setTitle("你在诉讼\\仲裁案件中，被对方律师下过什么套？");
        people.setContent("问题描述：本题已收录至知乎圆桌网络仲裁值多少，更多[仲裁]相关话题欢迎关注讨论。");
        people.setInfo("11 回答 · 1083 关注");

        List<FocusPeople> list = new ArrayList<FocusPeople>() {
            {
                add(people);
                add(people);
                add(people);
                add(people);
                add(people);
                add(people);
            }
        };

        Context context = getContext();
        if (context != null) {
            FocusAdapter adapter = new FocusAdapter(context, list);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(adapter);
            recyclerView.setFocusableInTouchMode(false);
        }
        return view;
    }
}

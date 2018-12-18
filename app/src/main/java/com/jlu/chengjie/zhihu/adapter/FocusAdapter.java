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

package com.jlu.chengjie.zhihu.adapter;

/*
 *@Author chengjie
 *@Date 2018-12-18
 *@Email chengjie.jlu@qq.com
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jlu.chengjie.zhihu.R;
import com.jlu.chengjie.zhihu.model.FocusPeople;

import java.util.List;

public class FocusAdapter extends RecyclerView.Adapter<FocusAdapter.ViewHolder> {

    private List<FocusPeople> list;
    private Context context;

    public FocusAdapter(Context context, List<FocusPeople> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.focus_people, viewGroup, false);
        return new FocusAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FocusPeople people = list.get(i);
        viewHolder.people.setText(people.getPeople());
        viewHolder.title.setText(people.getTitle());
        viewHolder.content.setText(people.getContent());
        viewHolder.info.setText(people.getInfo());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.people)
        TextView people;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.content)
        TextView content;

        @BindView(R.id.info)
        TextView info;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

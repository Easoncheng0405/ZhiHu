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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.gson.reflect.TypeToken;
import com.jlu.chengjie.zhihu.R;
import com.jlu.chengjie.zhihu.activity.LoginActivity;
import com.jlu.chengjie.zhihu.activity.ModifyPasswordActivity;
import com.jlu.chengjie.zhihu.activity.PersonalActivity;
import com.jlu.chengjie.zhihu.model.Response;
import com.jlu.chengjie.zhihu.model.User;
import com.jlu.chengjie.zhihu.net.OkHttpHelper;
import com.jlu.chengjie.zhihu.net.RequestCode;
import com.jlu.chengjie.zhihu.net.ServerHelper;
import com.jlu.chengjie.zhihu.util.*;
import com.vondear.rxui.view.dialog.RxDialogEditSureCancel;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;
import es.dmoral.toasty.Toasty;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Objects;

public class MyFragment extends Fragment {

    private final String TAG = "MyFragment";

    private SPUtil spUtil;
    private Activity context;
    private String phoneNum;
    private RxDialogEditSureCancel emailDialog;
    private RxDialogEditSureCancel nameDialog;

    @BindView(R.id.phone_number)
    TextView textViewPhone;

    @BindView(R.id.email_txt)
    TextView textViewEmail;

    @BindView(R.id.name)
    TextView titleName;

    @BindView(R.id.name_txt)
    TextView textViewName;

    @BindView(R.id.cache_size)
    TextView textViewCache;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment, container, false);
        ButterKnife.bind(this, view);
        context = this.getActivity();
        spUtil = new SPUtil(context);
        phoneNum = spUtil.getString(spUtil.KEY_PHONE);

        textViewPhone.setText(PhoneNumUtil.encryptPhone(phoneNum));
        textViewEmail.setText(spUtil.getString(spUtil.KEY_EMAIL));
        String name = spUtil.getString(spUtil.KEY_NAME);
        titleName.setText(name);
        textViewName.setText(name);
        textViewCache.setText(CacheUtil.getTotalCacheSize(context.getApplicationContext()));

        emailDialog = new RxDialogEditSureCancel(context);
        emailDialog.setTitle("输入邮箱地址");
        emailDialog.getTitleView().setTextColor(Color.BLACK);
        emailDialog.getTitleView().setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        emailDialog.getEditText().setHint("someone@example.com");
        emailDialog.getSureView().setOnClickListener(v -> setEmail());
        emailDialog.getCancelView().setOnClickListener(v -> emailDialog.cancel());

        nameDialog = new RxDialogEditSureCancel(context);
        nameDialog.setTitle("输入新昵称");
        nameDialog.getTitleView().setTextColor(Color.BLACK);
        nameDialog.getTitleView().setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        nameDialog.getSureView().setOnClickListener(v -> setName());
        nameDialog.getCancelView().setOnClickListener(v -> nameDialog.cancel());
        return view;
    }

    @OnClick(R.id.detail)
    void personalDetail() {
        startActivity(new Intent(getActivity(), PersonalActivity.class));
    }

    @OnClick(R.id.modify_pwd)
    void modifyPassword() {
        startActivity(new Intent(getActivity(), ModifyPasswordActivity.class));
    }

    @OnClick(R.id.email)
    void clickEmail() {
        emailDialog.show();
    }

    @OnClick(R.id.modify_name)
    void clickName() {
        nameDialog.show();
    }

    @OnClick(R.id.logout)
    void logout() {
        RxDialogSureCancel dialog = new RxDialogSureCancel(context);
        dialog.setTitle("确定要退出登录吗");
        dialog.getTitleView().setTextColor(Color.BLACK);
        dialog.getTitleView().setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        dialog.setCancelable(false);
        dialog.getContentView().setText("退出登录将清空缓存的个人数据并且下次需要重新登录");
        dialog.getSureView().setOnClickListener(v -> {
            spUtil.clearAll();
            startActivity(new Intent(context, LoginActivity.class));
            context.finish();
            dialog.dismiss();
        });

        dialog.getCancelView().setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @OnClick(R.id.clear_cache_view)
    void clearCache() {
        RxDialogSureCancel dialog = new RxDialogSureCancel(context);
        dialog.setTitle("要清除应用缓存吗");
        dialog.getTitleView().setTextColor(Color.BLACK);
        dialog.getTitleView().setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        dialog.getContentView().setText("清除应用缓存可能导致下一次打开速度变慢并且消耗更多的流量(不会退出您的账号)");
        dialog.getSureView().setOnClickListener(v -> {
            File dir = context.getCacheDir();
            if (dir != null && dir.delete()) {
                Toasty.success(context, "已清空应用缓存").show();
                CacheUtil.clearAllCache(context.getApplicationContext());
                textViewCache.setText(CacheUtil.getTotalCacheSize(context.getApplicationContext()));
            }
            dialog.dismiss();
        });

        dialog.getCancelView().setOnClickListener(v -> dialog.dismiss());
        dialog.setCancelable(false);
        dialog.show();
    }

    private void setEmail() {
        final String email = emailDialog.getEditText().getText().toString().trim();
        if (!email.matches(Regex.EMAIL)) {
            Toasty.error(context, "请输入正确的邮箱地址").show();
            return;
        }
        emailDialog.cancel();
        textViewEmail.setText(email);
        spUtil.putString(spUtil.KEY_EMAIL, email);
        TaskRunner.execute(() -> {
            String url = ServerHelper.getUrlSetEmail(phoneNum, email);
            Type type = new TypeToken<Response<Void>>() {
            }.getType();
            ZLog.d(TAG, "start to set email address.");
            final Response<Void> response = OkHttpHelper.get(url, type);
            Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                if (response == null) {
                    Toasty.error(context, "无法连接到服务器").show();
                } else {
                    if (response.getCode() == RequestCode.SUCCESS) {
                        Toasty.success(context, "成功更新邮箱地址").show();
                    } else {
                        Toasty.error(context, "更新邮箱地址失败,code: " + response.getCode()).show();
                    }
                }
            });

        });
    }

    private void setName() {
        final String name = nameDialog.getEditText().getText().toString().trim();
        if (name.length() < 3 || name.length() > 10) {
            Toasty.error(context, "昵称在3到10个字符之间").show();
            return;
        }
        nameDialog.cancel();
        titleName.setText(name);
        textViewName.setText(name);
        spUtil.putString(spUtil.KEY_NAME, name);
        TaskRunner.execute(() -> {
            String url = ServerHelper.getUrlSetName(phoneNum, name);
            Type type = new TypeToken<Response<User>>() {
            }.getType();
            ZLog.d(TAG, "start to set user name.");
            final Response<Void> response = OkHttpHelper.get(url, type);
            Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                if (response == null) {
                    Toasty.error(context, "无法连接到服务器").show();
                } else {
                    if (response.getCode() == RequestCode.SUCCESS) {
                        Toasty.success(context, "成功修改昵称").show();
                    } else {
                        Toasty.error(context, "更改昵称失败,code: " + response.getCode()).show();
                    }
                }
                textViewEmail.setText(spUtil.getString(spUtil.KEY_EMAIL));
            });

        });
    }
}

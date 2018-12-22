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
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.gson.reflect.TypeToken;
import com.jlu.chengjie.zhihu.R;
import com.jlu.chengjie.zhihu.model.Response;
import com.jlu.chengjie.zhihu.net.OkHttpHelper;
import com.jlu.chengjie.zhihu.net.RequestCode;
import com.jlu.chengjie.zhihu.net.ServerHelper;
import com.jlu.chengjie.zhihu.util.Regex;
import com.jlu.chengjie.zhihu.util.SPUtil;
import com.jlu.chengjie.zhihu.util.TaskRunner;
import com.jlu.chengjie.zhihu.util.ZLog;
import es.dmoral.toasty.Toasty;

import java.lang.reflect.Type;

public class ModifyPasswordActivity extends AppCompatActivity {

    private final String TAG = "ModifyPasswordActivity";

    private int step = 1;

    @BindView(R.id.first_step)
    View firstStep;

    @BindView(R.id.second_step)
    View secondStep;

    @BindView(R.id.last_step)
    View lastStep;

    @BindView(R.id.phone_number)
    EditText editTextPhone;

    @BindView(R.id.verify_code)
    EditText editTextCode;

    @BindView(R.id.pwd)
    EditText editTextPwd;

    @BindView(R.id.pwd_repeat)
    EditText editTextPwdRepeat;

    private SPUtil spUtil;
    private String code = "";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        context = this;
        ButterKnife.bind(this);
        spUtil = new SPUtil(context);
        ZLog.d(TAG, "enter modify password activity.");
    }

    @OnClick(R.id.phone_next)
    void verifyPhone() {
        final String phone = editTextPhone.getText().toString();
        if (TextUtils.isEmpty(phone) || !phone.matches(Regex.PHONE_NUM)) {
            Toasty.error(context, "请输入正确的手机号码").show();
            return;
        }
        step++;
        if (spUtil.getString(spUtil.KEY_PHONE).equals(phone))
            sendVerificationCode(phone);
        firstStep.setVisibility(View.GONE);
        secondStep.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.code_next)
    void verifyCode() {
        if (!TextUtils.isEmpty(code) && code.equals(editTextCode.getText().toString())) {
            step++;
            secondStep.setVisibility(View.GONE);
            lastStep.setVisibility(View.VISIBLE);
        } else {
            Toasty.error(context, "验证码不正确").show();
        }
    }

    @OnClick(R.id.finish)
    void modifyFinish() {
        final String phone = editTextPhone.getText().toString();
        final String pwd = editTextPwd.getText().toString();
        if (TextUtils.isEmpty(pwd) || pwd.length() < 6 || pwd.length() > 18) {
            Toasty.error(context, "密码长度在6到18位之间").show();
            return;
        }

        if (!pwd.equals(editTextPwdRepeat.getText().toString())) {
            Toasty.error(context, "两次密码不一致").show();
        }
        TaskRunner.execute(() -> {
            String url = ServerHelper.getUrlSetPwd(phone, pwd);
            Type type = new TypeToken<Response<Void>>() {
            }.getType();

            final Response<Void> response = OkHttpHelper.get(url, type);

            runOnUiThread(() -> {
                if (response == null) {
                    Toasty.error(context, "无法连接到服务器").show();
                } else {
                    if (response.getCode() == RequestCode.SUCCESS) {
                        ZLog.d(TAG, "modify password success.");
                        Toasty.success(context, "成功修改密码", Toast.LENGTH_LONG).show();
                        finish();
                    } else
                        Toasty.error(context, "服务器发生错误: " + response.getCode()).show();
                }
            });
        });
    }

    @OnClick(R.id.back)
    void backPressed() {
        if (step == 1) {
            finish();
            return;
        }
        if (step == 2) {
            secondStep.setVisibility(View.GONE);
            firstStep.setVisibility(View.VISIBLE);
            step--;
            return;
        }
        if (step == 3) {
            lastStep.setVisibility(View.GONE);
            secondStep.setVisibility(View.VISIBLE);
            step--;
        }
    }

    private void sendVerificationCode(final String phone) {
        TaskRunner.execute(() -> {
            String url = ServerHelper.getUrlSendCode(phone);
            Type type = new TypeToken<Response<String>>() {
            }.getType();

            ZLog.d(TAG, "start to send verification code.");
            final Response<String> response = OkHttpHelper.get(url, type);

            runOnUiThread(() -> {
                if (response == null) {
                    Toasty.error(context, "无法连接到服务器").show();
                } else {
                    if (response.getCode() == RequestCode.SUCCESS) {
                        code = response.getObject();
                        Toasty.success(context, "您的验证码是: " + code, Toast.LENGTH_LONG).show();
                        editTextCode.setText(code);
                    } else
                        Toasty.error(context, "服务器发生错误: " + response.getCode()).show();
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }
}

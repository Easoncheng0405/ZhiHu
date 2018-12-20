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
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.jlu.chengjie.zhihu.model.Response;
import com.jlu.chengjie.zhihu.model.User;
import com.jlu.chengjie.zhihu.net.NetWorkUtil;
import com.jlu.chengjie.zhihu.net.OkHttpHelper;
import com.jlu.chengjie.zhihu.net.RequestCode;
import com.jlu.chengjie.zhihu.net.ServerHelper;
import com.jlu.chengjie.zhihu.util.TaskRunner;
import es.dmoral.toasty.Toasty;

import com.jlu.chengjie.zhihu.R;
import com.jlu.chengjie.zhihu.util.ZLog;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";
    private Context context;
    private boolean isPwdLogin = false;

    @BindView(R.id.phone_number)
    EditText phone;

    @BindView(R.id.code)
    EditText code;

    @BindView(R.id.ok)
    Button ok;

    @BindView(R.id.login_type)
    TextView loginType;

    @BindView(R.id.send)
    TextView sender;

    private String verifyCode = "";

    private SweetAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        ButterKnife.bind(this);

        dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText("登录中，请稍后...");
        dialog.setCancelable(false);
    }

    /**
     * client accept two login types: password or mobile phone verification code
     * <p>
     * password: client send phone number and password to the server to login,
     * the server will return login result
     * <p>
     * verification code: client request server sent a verification code to the phone,
     * the server will return sent result and verification code detail
     *
     * @see #switchLoginType()
     * @see #isPwdLogin
     */
    @OnClick(R.id.ok)
    void ok() {
        if (!NetWorkUtil.isNetworkAvailable(context)) {
            Toasty.error(context, "网络不可用，检查您的网络设置", Toast.LENGTH_LONG, true).show();
            return;
        }
        String sPhone = phone.getText().toString().trim();
        String sPwd = code.getText().toString();
        if (isPwdLogin)
            pwdLogin(sPhone, sPwd);
        else {
            if (TextUtils.isEmpty(sPwd)) {
                Toasty.error(context, "输入验证码", Toast.LENGTH_LONG, true).show();
                return;
            }
            if (verifyCode.equals(sPwd)) {
                success();
            } else {
                Toasty.error(context, "验证码错误", Toast.LENGTH_LONG, true).show();
            }
        }
    }

    @OnClick(R.id.send)
    void sendVerificationCode() {
        String sPhone = phone.getText().toString().trim();
        if (TextUtils.isEmpty(sPhone)) {
            Toasty.error(context, "输入电话号码", Toast.LENGTH_LONG, true).show();
            return;
        }
        String url = ServerHelper.getUrlRegister(sPhone);
        ZLog.d(TAG, "start to send verification code.");
        try {
            TaskRunner.execute(() -> {
                Response<User> response = OkHttpHelper.get(url);
                runOnUiThread(() -> {
                    if (response != null) {
                        ZLog.d(TAG, "login response code: %d, message: %s", response.getCode(), response.getMsg());
                        switch (response.getCode()) {
                            case RequestCode.SUCCESS:
                                verifyCode = response.getNote();
                                code.setText(verifyCode);
                                Toasty.success(context, "您的验证码是: " + verifyCode, Toast.LENGTH_LONG).show();
                                break;
                            case RequestCode.SERVER_FATAL:
                                Toasty.info(context, "服务器发生错误", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                    } else
                        Toasty.error(context, "无法连接到服务器", Toast.LENGTH_LONG).show();
                });
            });
        } catch (Exception e) {
            ZLog.e(TAG, "fastLogin exception: ", e);
        }
        sender.setTextColor(Color.GRAY);
        sender.setEnabled(false);
        // after 60 seconds can send verification code code again.
        clock();
    }


    @OnClick(R.id.login_type)
    void switchLoginType() {
        isPwdLogin = !isPwdLogin;
        if (isPwdLogin) {
            code.setHint(R.string.input_pwd);
            ok.setText(R.string.btn_login);
            loginType.setText(R.string.fast_login);
            code.setInputType(InputType.TYPE_CLASS_TEXT);
            sender.setVisibility(View.GONE);
        } else {
            code.setHint(R.string.input_code);
            ok.setText("下一步");
            loginType.setText(R.string.pwd_login);
            code.setInputType(InputType.TYPE_CLASS_NUMBER);
            sender.setVisibility(View.VISIBLE);
        }

        //after switch login type,clear input content
        code.getText().clear();
        ZLog.d(TAG, "switch login type, is password login: " + isPwdLogin);
    }

    private void pwdLogin(String sPhone, String sPwd) {
        if (TextUtils.isEmpty(sPhone) || TextUtils.isEmpty(sPwd)) {
            Toasty.error(context, "输入电话号和密码", Toast.LENGTH_LONG, true).show();
            return;
        }
        String url = ServerHelper.getUrlLogin(sPhone, sPwd);
        ZLog.d(TAG, "start to login with password.");
        try {
            dialog.show();
            TaskRunner.execute(() -> {
                Response<User> response = OkHttpHelper.get(url);
                runOnUiThread(() -> {
                    if (response != null) {
                        ZLog.d(TAG, "login response code: %d, message: %s", response.getCode(), response.getMsg());
                        switch (response.getCode()) {
                            case RequestCode.SUCCESS:
                                success();
                                break;
                            case RequestCode.NOT_FOUND:
                                Toasty.info(context, "手机号码尚未注册，请切换到快捷登录以注册", Toast.LENGTH_LONG).show();
                                break;
                            case RequestCode.SERVER_FATAL:
                                Toasty.info(context, "服务器发生错误", Toast.LENGTH_LONG).show();
                                break;
                            case RequestCode.VERIFY_FAILED:
                                Toasty.error(context, "手机号码与密码不匹配", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                    } else
                        Toasty.error(context, "无法连接到服务器", Toast.LENGTH_LONG).show();
                    dialog.cancel();
                });
            });
        } catch (Exception e) {
            ZLog.e(TAG, "login exception: ", e);
        }
    }

    private void success() {
        startActivity(new Intent(context, MainActivity.class));
        // this activity will not be used again, finish it.
        ZLog.d(TAG, "login success! finish login activity.");
        finish();
    }

    private void clock() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                sender.setText("已发送(" + millisUntilFinished / 1000 + "s)");
            }

            @Override
            public void onFinish() {
                sender.setEnabled(true);
                sender.setText("重新获取验证码");
                sender.setTextColor(Color.parseColor("#1296db"));
            }
        }.start();
    }
}

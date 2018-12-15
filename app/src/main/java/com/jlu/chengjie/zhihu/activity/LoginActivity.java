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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.jlu.chengjie.zhihu.R;

public class LoginActivity extends AppCompatActivity {

    private boolean pwdLogin = false;

    @BindView(R.id.phone_number)
    EditText phone;

    @BindView(R.id.code)
    EditText code;

    @BindView(R.id.login)
    Button login;

    @BindView(R.id.login_type)
    TextView loginType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login)
    void login() {
        if (pwdLogin) {
            Toasty.error(this, "密码错误!", Toast.LENGTH_LONG, true).show();
        } else {
            Toasty.success(this, "您的验证码是: 123456 ", Toast.LENGTH_LONG, true).show();
        }
    }

    @OnClick(R.id.login_type)
    void switchLoginType() {
        pwdLogin = !pwdLogin;
        if (pwdLogin) {
            code.setHint(R.string.input_pwd);
            login.setText(R.string.btn_login);
            loginType.setText(R.string.fast_login);
            code.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            code.setHint(R.string.input_code);
            login.setText(R.string.verification_code);
            loginType.setText(R.string.pwd_login);
            code.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        //after switch login type,clear input content
        code.getText().clear();
    }
}

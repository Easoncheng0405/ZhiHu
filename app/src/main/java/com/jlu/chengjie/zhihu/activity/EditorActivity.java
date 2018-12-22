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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.reflect.TypeToken;
import com.jlu.chengjie.zhihu.R;
import com.jlu.chengjie.zhihu.model.Question;
import com.jlu.chengjie.zhihu.model.Response;
import com.jlu.chengjie.zhihu.net.OkHttpHelper;
import com.jlu.chengjie.zhihu.net.RequestCode;
import com.jlu.chengjie.zhihu.net.ServerHelper;
import com.jlu.chengjie.zhihu.util.SPUtil;
import com.jlu.chengjie.zhihu.util.TaskRunner;
import com.jlu.chengjie.zhihu.util.ZLog;
import es.dmoral.toasty.Toasty;
import jp.wasabeef.richeditor.RichEditor;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class EditorActivity extends AppCompatActivity {

    private final String TAG = "EditorActivity";

    private RichEditor mEditor;
    private EditText mTitle;
    private View mToolBar;
    private SPUtil spUtil;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        context = this;
        spUtil = new SPUtil(context);

        mEditor = findViewById(R.id.editor);
        mTitle = findViewById(R.id.title);
        mToolBar = findViewById(R.id.tool_bar);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setPadding(10, 10, 10, 10);

        mEditor.setPlaceholder("输入问题内容");

        mTitle.setOnFocusChangeListener((v, hasFocus) -> mToolBar.setVisibility(hasFocus ? View.INVISIBLE : View.VISIBLE));

        findViewById(R.id.next).setOnClickListener(v -> editFinish());

        findViewById(R.id.cancel).setOnClickListener(v -> onBackPressed());

        findViewById(R.id.action_undo).setOnClickListener(v -> mEditor.undo());

        findViewById(R.id.action_redo).setOnClickListener(v -> mEditor.redo());

        findViewById(R.id.action_bold).setOnClickListener(v -> mEditor.setBold());

        findViewById(R.id.action_italic).setOnClickListener(v -> mEditor.setItalic());

        findViewById(R.id.action_subscript).setOnClickListener(v -> mEditor.setSubscript());

        findViewById(R.id.action_superscript).setOnClickListener(v -> mEditor.setSuperscript());

        findViewById(R.id.action_strikethrough).setOnClickListener(v -> mEditor.setStrikeThrough());

        findViewById(R.id.action_underline).setOnClickListener(v -> mEditor.setUnderline());

        findViewById(R.id.action_heading1).setOnClickListener(v -> mEditor.setHeading(1));

        findViewById(R.id.action_heading2).setOnClickListener(v -> mEditor.setHeading(2));

        findViewById(R.id.action_heading3).setOnClickListener(v -> mEditor.setHeading(3));

        findViewById(R.id.action_heading4).setOnClickListener(v -> mEditor.setHeading(4));

        findViewById(R.id.action_heading5).setOnClickListener(v -> mEditor.setHeading(5));

        findViewById(R.id.action_heading6).setOnClickListener(v -> mEditor.setHeading(6));

        findViewById(R.id.action_indent).setOnClickListener(v -> mEditor.setIndent());

        findViewById(R.id.action_outdent).setOnClickListener(v -> mEditor.setOutdent());

        findViewById(R.id.action_align_left).setOnClickListener(v -> mEditor.setAlignLeft());

        findViewById(R.id.action_align_center).setOnClickListener(v -> mEditor.setAlignCenter());

        findViewById(R.id.action_align_right).setOnClickListener(v -> mEditor.setAlignRight());

        findViewById(R.id.action_blockquote).setOnClickListener(v -> mEditor.setBlockquote());

        findViewById(R.id.action_insert_bullets).setOnClickListener(v -> mEditor.setBullets());

        findViewById(R.id.action_insert_numbers).setOnClickListener(v -> mEditor.setNumbers());

        findViewById(R.id.action_insert_image).setOnClickListener(v -> mEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                "dachshund"));

        findViewById(R.id.action_insert_link).setOnClickListener(v -> mEditor.insertLink("https://github.com/wasabeef", "wasabeef"));
        findViewById(R.id.action_insert_checkbox).setOnClickListener(v -> mEditor.insertTodo());
    }

    private void editFinish() {
        String phone = spUtil.getString(spUtil.KEY_PHONE, "");
        if (TextUtils.isEmpty(phone)) {
            ZLog.w(TAG, "");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        final String title = mTitle.getText().toString();
        if (TextUtils.isEmpty(title) || title.length() < 5 || title.length() > 50) {
            Toasty.error(this, "标题长度在5到50个字符之间").show();
            return;
        }

        final String cotent = mEditor.getHtml();
        if (cotent == null || cotent.length() < 10) {
            Toasty.error(this, "问题内容不少于10个字符").show();
            return;
        }
        if (cotent.length() > 1000) {
            Toasty.error(this, "问题内容过长").show();
            return;
        }

        final Map<String, String> params = new HashMap<String, String>() {
            {
                put("phone", phone);
                put("title", title);
                put("content", cotent);
            }
        };

        ZLog.d(TAG, "start to submit a new question,phone: %s,title: %s,content: %s", phone, title, cotent);
        TaskRunner.execute(() -> {
            Type type = new TypeToken<Response<Question>>() {
            }.getType();
            final Response<Question> response = OkHttpHelper.post(ServerHelper.getUrlQuestion(), type, params);
            runOnUiThread(() -> {
                if (response == null) {
                    Toasty.error(context, "无法连接到服务器", Toast.LENGTH_LONG, true).show();
                    return;
                }
                if (response.getCode() == RequestCode.SUCCESS) {
                    Toasty.success(context, "成功发布问题", Toast.LENGTH_LONG, true).show();
                    finish();
                } else {
                    Toasty.error(context, "发布问题失败, code: " + response.getCode(), Toast.LENGTH_LONG, true).show();
                }
            });

        });
    }

}

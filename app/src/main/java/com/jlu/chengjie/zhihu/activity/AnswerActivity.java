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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fanneng.android.web.NestedScrollSuperWebView;
import com.jlu.chengjie.zhihu.R;
import com.jlu.chengjie.zhihu.util.ZLog;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class AnswerActivity extends AppCompatActivity {

    private final String TAG = "AnswerActivity";

    @BindView(R.id.web_view)
    NestedScrollSuperWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        ButterKnife.bind(this);
        String html = "<a href=\"https://www.baidu.com/\">wasabeef</a>";

        WebViewClient client = new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                ZLog.d(TAG, "load url: " + url);
                Intent intent = new Intent(AnswerActivity.this, WebViewActivity.class);
                intent.setAction(url);
                startActivity(intent);
                return true;
            }
        };
        webView.setWebViewClient(client);
        webView.loadData(html, "text/html; charset=UTF-8", null);
    }
}

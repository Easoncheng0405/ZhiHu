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

public class QuestionActivity extends AppCompatActivity {

    private final String TAG = "QuestionActivity";

    @BindView(R.id.content)
    NestedScrollSuperWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);

        String html = "<a href=\"https://www.baidu.com/\">wasabeef</a>";

        WebViewClient client = new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                ZLog.d(TAG, "load url: " + url);
                Intent intent = new Intent(QuestionActivity.this, WebViewActivity.class);
                intent.setAction(url);
                startActivity(intent);
                return true;
            }
        };
        webView.setWebViewClient(client);
        webView.loadData(html, "text/html; charset=UTF-8", null);
    }
}

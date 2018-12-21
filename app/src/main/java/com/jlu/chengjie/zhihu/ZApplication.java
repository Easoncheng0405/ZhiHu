package com.jlu.chengjie.zhihu;

/*
 *@Author chengjie
 *@Date 2018-12-21
 *@Email chengjie.jlu@qq.com
 */

import android.app.Application;
import org.litepal.LitePal;

public class ZApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}

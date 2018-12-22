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

package com.jlu.chengjie.zhihu.util;

/*
 *@Author chengjie
 *@Date 2018-12-21
 *@Email chengjie.jlu@qq.com
 */

import android.content.Context;
import android.content.SharedPreferences;

@SuppressWarnings("unused")
public class SPUtil {

    public final String KEY_PHONE = "phone";
    public final String KEY_EMAIL = "email";

    private SharedPreferences preferences;

    public SPUtil(Context context) {
        final String SP_NAME = "ZhiHu.xml";
        context = context.getApplicationContext();
        preferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String def) {
        return preferences.getString(key, def);
    }

    public void clearAll() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}

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

package com.jlu.chengjie.zhihu.net;

/*
 *@Author chengjie
 *@Date 2018-12-20
 *@Email chengjie.jlu@qq.com
 */


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jlu.chengjie.zhihu.model.Response;
import com.jlu.chengjie.zhihu.util.ZLog;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.concurrent.TimeUnit;

public class OkHttpHelper {

    private static final String TAG = "OkHttpHelper";
    private static final int TIMEOUT = 5000;

    public static <T> Response<T> get(String url) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                    .build();
            final Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            Call call = okHttpClient.newCall(request);
            String result = call.execute().body().string();
            ZLog.d(TAG, "url: " + url + " response: " + result);
            return new Gson().fromJson(result, new TypeToken<Response<T>>() {
            }.getType());
        } catch (Exception e) {
            ZLog.e(TAG, "get method exception: ", e);
        }
        return null;
    }
}
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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.jlu.chengjie.zhihu.util.ZLog;

@SuppressWarnings("unused")
public class NetWorkUtil {

    private static final String TAG = "NetWorkUtil";

    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        } catch (Exception e) {
            ZLog.e(TAG, "isNetworkAvailable exception", e);
        }
        return false;
    }

    public static boolean isWifiAvailable(Context context) {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        } catch (Exception e) {
            ZLog.e(TAG, "isWifiAvailable exception", e);
        }
        return false;
    }


    public static boolean isMobileAvailable(Context context) {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        } catch (Exception e) {
            ZLog.e(TAG, "isMobileAvailable exception", e);
        }
        return false;
    }

    public static int getConnectedType(Context context) {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        } catch (Exception e) {
            ZLog.e(TAG, "getConnectedType exception", e);
        }
        return -1;
    }
}

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
 *@Date 2018-12-17
 *@Email chengjie.jlu@qq.com
 */

import android.content.Context;

public class PixelUtil {

    private PixelUtil() {
        throw new AssertionError("No com.jlu.chengjie.zhihu.util.PixelUtil instances for you!");
    }

    public static int px2dp(Context context, float pxValue) {
        return (int) (pxValue / getScale(context) + 0.5f);
    }

    public static float dp2px(Context context, int dp) {
        return getScale(context) * dp;
    }

    private static float getScale(Context context){
        return context.getResources().getDisplayMetrics().density;
    }

}

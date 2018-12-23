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
 *@Date 2018-12-22
 *@Email chengjie.jlu@qq.com
 */

public class PhoneNumUtil {

    private PhoneNumUtil() {
        throw new AssertionError("No com.jlu.chengjie.zhihu.util.PhoneNumUtil instances for you!");
    }

    public static boolean isPhoneNum(String phoneNum) {
        return phoneNum != null && phoneNum.matches(Regex.PHONE_NUM);
    }

    public static String encryptPhone(String phoneNum) {
        return isPhoneNum(phoneNum) ? phoneNum.substring(0, 3) + "****" + phoneNum.substring(7, 11) : "";
    }


}

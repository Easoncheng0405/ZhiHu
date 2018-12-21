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

public class ServerHelper {

    private static final String HOST = "10.151.130.210";

    private static final String PORT = "8080";

    private static final String HTTP_METHOD = "http://";

    private static final String SERVER_URL = HTTP_METHOD + HOST + ":" + PORT;

    private static final String URL_LOGIN = "/pwdLogin?phone=%s&pwd=%s";

    private static final String URL_REGISTER = "/fastLogin?phone=%s";

    private static final String URL_QUESTION = "/question/add";

    public static String getUrlLogin(String... args) {
        return String.format(SERVER_URL + URL_LOGIN, (Object[]) args);
    }

    public static String getUrlRegister(String phone) {
        return String.format(SERVER_URL + URL_REGISTER, phone);
    }

    public static String getUrlQuestion() {
        return String.format(SERVER_URL + URL_QUESTION);
    }
}

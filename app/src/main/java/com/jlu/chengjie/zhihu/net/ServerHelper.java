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

    private static final String HOST = "47.94.134.55";

    private static final String PORT = "80";

    private static final String HTTP_METHOD = "http://";

    private static final String SERVER_URL = HTTP_METHOD + HOST + ":" + PORT;

    private static final String URL_LOGIN = SERVER_URL + "/pwdLogin?phone=%s&pwd=%s";

    private static final String URL_REGISTER = SERVER_URL + "/fastLogin?phone=%s";

    private static final String URL_QUESTION = SERVER_URL + "/question/add";

    private static final String URL_SET_EMAIL = SERVER_URL + "/user/email?phone=%s&email=%s";

    private static final String URL_SEND_CODE = SERVER_URL + "/user/code?phone=%s";

    private static final String URL_SET_PWD = SERVER_URL + "/user/pwd?phone=%s&pwd=%s";

    public static String getUrlLogin(String... args) {
        return String.format(URL_LOGIN, (Object[]) args);
    }

    public static String getUrlRegister(String phone) {
        return String.format(URL_REGISTER, phone);
    }

    public static String getUrlQuestion() {
        return URL_QUESTION;
    }

    public static String getUrlSetEmail(String... args) {
        return String.format(URL_SET_EMAIL, (Object[]) args);
    }

    public static String getUrlSendCode(String phone) {
        return String.format(URL_SEND_CODE, phone);
    }

    public static String getUrlSetPwd(String phone, String pwd) {
        return String.format(URL_SET_PWD, phone, pwd);
    }

}

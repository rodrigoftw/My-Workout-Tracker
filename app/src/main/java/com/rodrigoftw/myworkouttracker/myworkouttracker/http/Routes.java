package com.rodrigoftw.myworkouttracker.myworkouttracker.http;

import com.rodrigoftw.myworkouttracker.myworkouttracker.util.Util;

/**
 * Created by Rodrigo on 01/05/2017.
 */
public class Routes {
    public static final String DEFAULT_BASE_APP = "http://myworkouttracker.com/";
    public static String BASE_APP = null;
    public static final String BASE_API = "api/";
    public static final String LOGIN = "user/login";
    public static final String SOCIAL_LOGIN = "user/social/login";
    public static final String REGISTER = "user/register";
    public static final String LIST_EXERCISES = "exercise";
    public static final String UPLOAD = "upload/";
    public static final String USER_ME = "me";
    public static final String EXERCISE_SHOW = "exercise/{id}";
    public static final String USER_UPDATE = "user/update";
    public static final String FORGOT_PASSWORD = "user/forgot-password";

    public static String setBaseApp() {
        BASE_APP = Util.getBaseApp();
        return BASE_APP;
    }

    public static String getApiRoute(String route) {
        return BASE_APP + BASE_API + route + "?token=" + Util.getTokenAccess();
    }

    public static String getRoute(String route) {
        return BASE_APP + route;
    }
}

package com.ayvytr.commonlibrary;

/**
 * @author admin
 */
public class Env {
    private static boolean isDebug = true;

    public static final String BASE_URL = "http://gank.io/api/";

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean isDebug) {
        Env.isDebug = isDebug;
    }
}

package com.ayvytr.commonlibrary.util;

/**
 * @author wangdunwei
 */
public enum NetworkState {
    LOADING,
    SUCCESS,
    FAILED;

    public static NetworkState of(boolean succeed) {
        return succeed ? SUCCESS : FAILED;
    }
}

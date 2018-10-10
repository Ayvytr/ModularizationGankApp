package com.ayvytr.commonlibrary;

/**
 * @author admin
 */
public enum GankType {
    ANDROID("Android"),
    IOS("iOS"),
    REST_VIDEO("休息视频"),
    GIRLS("福利"),
    MORE_RES("拓展资源"),
    WEB("前端"),
    RECOMMEND("瞎推荐"),
    APP("App");

    private String key;

    GankType(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return this.key;
    }
}

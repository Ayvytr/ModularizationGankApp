package com.ayvytr.commonlibrary

/**
 * @author admin
 */
enum class GankType private constructor(private val key: String) {
    ANDROID("Android"),
    IOS("iOS"),
    REST_VIDEO("休息视频"),
    GIRLS("福利"),
    MORE_RES("拓展资源"),
    WEB("前端"),
    RECOMMEND("瞎推荐"),
    APP("App");

    override fun toString(): String {
        return this.key
    }
}

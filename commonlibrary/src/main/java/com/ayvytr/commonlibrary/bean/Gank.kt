package com.ayvytr.commonlibrary.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @author admin
 */
class Gank : Parcelable {
    /**
     * _id : 5bba1b899d212261127b79d1
     * createdAt : 2018-10-07T14:43:21.406Z
     * desc : Android自动屏幕适配插件，大大减轻你和UI设计师的工作量
     * images : ["https://ww1.sinaimg.cn/large/0073sXn7ly1fw0vipvym5j30ny09o758","https://ww1.sinaimg.cn/large/0073sXn7ly1fw0vipycxjj30gy09gt9j"]
     * publishedAt : 2018-10-08T00:00:00.0Z
     * source : web
     * type : Android
     * url : http://tangpj.com/2018/09/29/calces-screen/
     * used : true
     * who : PJ Tang
     */

    var _id: String? = null

    var createdAt: String? = null

    var desc: String? = null

    var publishedAt: String? = null

    var source: String? = null

    var type: String? = null

    var url: String? = null

    var isUsed: Boolean = false

    var who: String? = null

    var images: List<String>? = null

    var isHeader: Boolean = false

    constructor(type: String, isHeader: Boolean) {
        this.type = type
        this.isHeader = isHeader
    }

    constructor() {}

    constructor(source: Parcel) : this(
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Gank> = object : Parcelable.Creator<Gank> {
            override fun createFromParcel(source: Parcel): Gank = Gank(source)
            override fun newArray(size: Int): Array<Gank?> = arrayOfNulls(size)
        }
    }
}

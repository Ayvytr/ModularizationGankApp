package com.ayvytr.commonlibrary.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Do
 */
@Parcelize
data class TodayInHistory(

    var msg: String? = null,
    var retCode: String? = null,
    //        msg	string	是	返回说明
    //        result	string	是	返回结果集
    var result: List<ResultBean>? = null
) : Parcelable {

    @Parcelize
    data class ResultBean(
        /**
         * date : 19821231
         * day : 31
         * event : 1982年12月31日加纳陆军接管了政权。
         * 加纳新的军人领袖罗林斯这天在电台发表讲话说，加纳陆军已经接管了1979年9月执政的利曼总统的政府，开始执政。这是罗林斯在三年内第二次掌权。
         *
         * id : 569881b8590146d407333528
         * month : 12
         * title : 加纳发生军事政变
         */
        //        date	string	是	日期
        //        month	string	是	月份
        //        day	string	是	天
        //        title	int	是	标题
        //        event	int	是	事件
        var date: String? = null,
        var day: Int = 0,
        var event: String? = null,
        var id: String? = null,
        var month: Int = 0,
        var title: String? = null
    ) : Parcelable
}

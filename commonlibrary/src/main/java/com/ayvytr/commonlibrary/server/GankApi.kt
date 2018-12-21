package com.ayvytr.commonlibrary.server

import com.ayvytr.commonlibrary.bean.BaseGank
import com.ayvytr.commonlibrary.bean.GankHistory
import com.ayvytr.commonlibrary.bean.GankHistoryContent

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * 干货网api接口
 *
 * @author ayvytr
 */
interface GankApi {

    /**
     * 获取发过干货日期接口
     */
    @get:GET("day/history")
    val publishDate: Observable<GankHistory>

    /**
     * 获取最新一天的干货
     */
    @GET("today")
    fun today(): Observable<*>

    /**
     * 搜索
     * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * count 最大 50
     */
    @GET("search/query/{key}/category/{type}/count/{pageSize}/page/{currentPage}")
    fun search(@Path("key") key: String, @Path("type") keyType: String, @Path("pageSize") pageSize: Int,
               @Path("currentPage") currentPage: Int): Observable<BaseGank>

    /**
     * 获取某几日干货网站数据
     */
    @GET("history/content/{pageSize}/{currentPage}")
    fun getHistoryDataByCount(@Path("pageSize") pageSize: Int, @Path("currentPage") currentPage: Int): Observable<*>

    /**
     * 支持提交干货到审核区
     *
     * @param url     想要提交的网页地址
     * @param desc    对干货内容的描述
     * @param who     干货提交者的网络 ID
     * @param type    干货类型。可选参数: Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * @param isDebug 当前提交为测试数据
     * @return
     */
    @POST("add2gank")
    fun pushGank(@Field("url") url: String, @Field("desc") desc: String, @Field("who") who: String,
                 @Field("type") type: String, @Field("debug") isDebug: Boolean): Observable<*>

    /**
     * 分类数据
     */
    @GET("data/{type}/{pageSize}/{currentPage}")
    fun getDataByType(@Path("type") type: String, @Path("pageSize") pageSize: Int,
                      @Path("currentPage") currentPage: Int): Observable<BaseGank>

    /**
     * 每日数据
     */
    @GET("day/{year}/{month}/{day}")
    fun getDataByDate(@Path("year") year: String, @Path("month") month: String,
                      @Path("day") dayOfMonth: String): Observable<GankHistoryContent>

    @GET
    fun getImageData(@Url url: String): Observable<ResponseBody>
}


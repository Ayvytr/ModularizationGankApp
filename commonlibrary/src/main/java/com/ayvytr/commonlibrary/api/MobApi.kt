package com.ayvytr.commonlibrary.api

import com.ayvytr.mob.TodayOfHistory
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Do
 */
interface MobApi {
    /**
     * 历史上的今天
     * @param key Mob appkey
     * @param day 日期(格式:MMdd)
     */
    @GET("appstore/history/query")
    fun todayOfHistory(@Query("key") key: String, @Query("day") day: String): Observable<TodayOfHistory>

    /**
     * 健康知识查询接口
     * @param key     string	是	用户申请的appkey
     * @param name    string	是	关键字
     * @param page    int	    否	当前页（默认为1）
     * @param size    int	    否	每页显示页大小（默认位20）
     */
    @GET("appstore/health/search")
    fun health(@Query("key") key: String, @Query("name") name: String, @Query("page") page: Int, @Query("size") pageSize: Int)

    /**
     * 菜谱分类标签查询
     * @param key    string	是	用户申请的appkey
     */
    @GET("v1/cook/category/query")
    fun cookCategory(@Query("key") key: String)

    /**
     * 按标签查询菜谱接口
     * @param   key     string	是	用户申请的appkey
     * @param   cid     string	否	标签ID(末级分类标签)
     * @param   name    string	否	菜谱名称	红烧肉
     * @param   page    int	    否	起始页(默认1)
     * @param   size    int	    否	返回数据条数(默认20)
     */
    @GET("v1/cook/menu/search")
    fun cookMenu(@Query("key") key: String, @Query("cid") cid: String, @Query("name") name: String, @Query("page") page: Int, @Query("size") pageSize: Int)

    /**
     * 菜谱查询接口
     * @param key	string	是	用户申请的appkey
     * @param id	string	是	菜谱ID
     */
    @GET("v1/cook/menu/query")
    fun cookMenuDetail(@Query("key") key: String, @Query("id") id: String)
}
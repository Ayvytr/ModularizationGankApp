package com.ayvytr.commonlibrary.server;

import com.ayvytr.commonlibrary.bean.BaseGank;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 干货网api接口
 *
 * @author ayvytr
 */
public interface GankApi {
    /**
     * 获取最新一天的干货
     */
    @GET("today")
    Observable today();

    /**
     * 搜索
     * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * count 最大 50
     */
    @GET("search/query/listview/category/{type}/count/{pageSize}/page/{currentPage}")
    Observable search(@Path("type") String keyType, @Path("pageSize") int pageSize,
                      @Path("currentPage") int currentPage);

    /**
     * 获取某几日干货网站数据
     */
    @GET("history/content/{pageSize}/{currentPage}")
    Observable getHistoryDataByCount(@Path("pageSize") int pageSize, @Path("currentPage") int currentPage);

    /**
     * 获取特定日期网站数据:
     */
    @GET("history/content/day/{year}/{month}/{day}")
    Observable getHistoryData(@Path("year") int year, @Path("month") int month, @Path("day") int dayOfMonth);

    /**
     * 获取发过干货日期接口
     */
    @GET("day/history")
    Observable getPublishDate();

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
    Observable pushGank(@Field("url") String url, @Field("desc") String desc, @Field("who") String who,
                        @Field("type") String type, @Field("debug") boolean isDebug);

    /**
     * 分类数据
     */
    @GET("data/{type}/{pageSize}/{currentPage}")
    Observable<BaseGank> getDataByType(@Path("type") String type, @Path("pageSize") int pageSize,
                                       @Path("currentPage") int currentPage);

    /**
     * 每日数据
     */
    @GET("day/{year{/{month}/{day}")
    Observable getDataByDate(@Path("year") int year, @Path("month") int month, @Path("day") int dayOfMonth);
}


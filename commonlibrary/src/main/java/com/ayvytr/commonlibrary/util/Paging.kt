package com.ayvytr.commonlibrary.util

import android.arch.paging.PagedList

/**
 * @author wangdunwei
 */
object Paging {
    const val DEFAULT_PAGE_SIZE = 10

    @JvmField
    val config = PagedList.Config.Builder()
        .setPageSize(DEFAULT_PAGE_SIZE)                         //配置分页加载的数量
        .setEnablePlaceholders(false)     //配置是否启动PlaceHolders
        .setInitialLoadSizeHint(DEFAULT_PAGE_SIZE)              //初始化加载的数量
        .build()
}

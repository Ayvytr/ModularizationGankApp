package com.ayvytr.commonlibrary.bean

import java.util.HashMap

/**
 * @author admin
 */
class GankHistoryContent {
    var isError: Boolean = false
    var category: List<String>? = null
    var results: HashMap<String, List<Gank>>? = null
}

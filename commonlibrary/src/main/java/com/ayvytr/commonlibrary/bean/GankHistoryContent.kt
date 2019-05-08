package com.ayvytr.commonlibrary.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author admin
 */
@Parcelize
data class GankHistoryContent(
    var isError: Boolean = false,
    var category: List<String>? = null,
    var results: HashMap<String, List<Gank>>? = null
) : Parcelable

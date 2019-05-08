package com.ayvytr.commonlibrary.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author admin
 */
@Parcelize
data class GankHistory(
    var isError: Boolean = false,
    var results: List<String>? = null)
    : Parcelable

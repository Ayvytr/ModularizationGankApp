package com.ayvytr.commonlibrary.bean

/**
 * @author Do
 */
open class BaseMob {
    var msg: String? = null
    var retCode: String? = null

    fun isSucceed(): Boolean{
        return retCode == "200"
    }
}

package com.ayvytr.commonlibrary

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * @author admin
 */
object IntentUtil {
    fun startAppShareText(context: Context, shareTitle: String, shareText: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain" // 纯文本
        shareIntent.putExtra(Intent.EXTRA_TITLE, shareTitle)
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"))
    }

    fun startAppShareImage(context: Context, shareTitle: String, shareText: String, uri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.putExtra(Intent.EXTRA_TITLE, shareTitle)
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"))
    }

    fun getShareImageIntent(shareTitle: String, shareText: String, uri: Uri): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.putExtra(Intent.EXTRA_TITLE, shareTitle)
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
        return shareIntent
    }

    fun getShareTextIntent(shareTitle: String, shareText: String): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain" // 纯文本
        shareIntent.putExtra(Intent.EXTRA_TITLE, shareTitle)
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
        return shareIntent
    }
}

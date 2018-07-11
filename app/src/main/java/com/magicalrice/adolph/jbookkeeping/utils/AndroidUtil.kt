package com.magicalrice.adolph.jbookkeeping.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import com.magicalrice.adolph.jbookkeeping.R

/**
 * Created by Adolph on 2018/7/11.
 */
object AndroidUtil {
    /**
     * 使用系统发送分享数据
     *
     * @param context 上下文
     * @param text    要分享的文本
     */
    fun share(context: Context,text: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT,text)
        sendIntent.type = "text/plain"
        context.startActivity(Intent.createChooser(sendIntent,context.getString(R.string.text_share_to)))
    }

    /**
     * 通过浏览器打开
     * @param url 要打开的 Url
     */
    fun openWeb(context: Context,url:String) {
        val builder = CustomTabsIntent.Builder()
        //github 黑色
        builder.setToolbarColor(Color.parseColor("#ff24292d"))
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }
}
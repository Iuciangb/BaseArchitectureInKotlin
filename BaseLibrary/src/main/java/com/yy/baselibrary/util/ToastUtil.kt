package com.yy.baselibrary.util

import android.content.Context
import android.os.Looper
import android.view.Gravity

import android.widget.Toast


/**
 * @author YY
 * Created 2022/3/20
 * Description：
 */
object ToastUtil {
    fun showToast(context: Context, messageId: Int) {
        showToast(context, context.getString(messageId))
    }

    fun showToast(context: Context?, message: String?) {
        if (Looper.myLooper() == null) {
            Looper.prepare()
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).also {
            it.setGravity(Gravity.CENTER, 0, 0)
            it.show()
        }
        Looper.loop()
    }

    fun showLongToast(context: Context, messageId: Int) {
        showLongToast(context, context.getString(messageId))
    }

    fun showLongToast(context: Context?, message: String?) {
        if (Looper.myLooper() == null) {
            Looper.prepare()
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).also {
            it.setGravity(Gravity.CENTER, 0, 0)
            it.show()
        }
        Looper.loop()
    }
}
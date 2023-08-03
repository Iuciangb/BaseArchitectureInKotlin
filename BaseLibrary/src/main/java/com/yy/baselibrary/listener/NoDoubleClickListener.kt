package com.yy.baselibrary.listener

import android.view.View
import java.util.*

/**
 * @author YY
 * @create 2022/8/23
 * @Describe
 **/
abstract class NoDoubleClickListener : View.OnClickListener {
    private var lastClickTime: Long = 0

    companion object{
        private const val MIN_CLICK_DELAY_TIME = 1000
    }

    override fun onClick(v: View?) {
        val currentTime = Calendar.getInstance().timeInMillis
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime
            onNoDoubleClick(v)
        }
    }

    open abstract fun onNoDoubleClick(view: View?)
}
package com.yy.baselibrary.util

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * @author YY
 * Created 2022/2/28
 * Description：
 */
class ActivityManager private constructor() {
    companion object {
        private val sActivityManager: ActivityManager? = null
        private val sActivityList: MutableList<Activity> = ArrayList()
        val instance: ActivityManager?
            get() = sActivityManager ?: ActivityManager()
    }

    fun addActivity(activity: Activity) {
        sActivityList.add(activity)
    }

    fun removeActivity(activity: Activity) {
        sActivityList.remove(activity)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun finish() {
        for (activity in sActivityList) {
            if (activity.isDestroyed) {
                continue
            }
        }
        sActivityList.clear()
    }
}
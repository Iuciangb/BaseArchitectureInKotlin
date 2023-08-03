package com.yy.baselibrary.util

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration

/**
 * @author YY
 * Created 2022/2/28
 * Description：
 */
object FitUtil {
    /**
     * 設計圖上的寬度，目前以360為例
     */
    private const val DESIGNED_WIDTH: Int = 360

    // 系统的Density
    private var sNonCompatDensity = 0f

    // 系统的ScaledDensity
    private var sNonCompatScaledDensity = 0f

    /**
     * 今日頭條適配方案
     * 在底頁Activity setContentView之前call，會根據預設設計圖寬度自動調整
     *
     * @param activity
     * @param application
     */
    fun setCustomDensity(activity: Activity, application: Application) {
        val displayMetrics = application.resources.displayMetrics
        if (sNonCompatDensity == 0f) {
            sNonCompatDensity = displayMetrics.density
            sNonCompatScaledDensity = displayMetrics.scaledDensity
            // 監聽在系統設定中切换字體
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNonCompatScaledDensity = application.resources.displayMetrics.scaledDensity
                    }
                }

                override fun onLowMemory() {}
            })
        }
        val targetDensity = displayMetrics.widthPixels / DESIGNED_WIDTH.toFloat()
        val targetScaledDensity = targetDensity * (sNonCompatScaledDensity / sNonCompatDensity)
        val targetDensityDpi = (160 * targetDensity).toInt()
        displayMetrics.density = targetDensity
        displayMetrics.scaledDensity = targetScaledDensity
        displayMetrics.densityDpi = targetDensityDpi
        val activityDisplayMetrics = activity.resources.displayMetrics
        activityDisplayMetrics.density = targetDensity
        activityDisplayMetrics.scaledDensity = targetScaledDensity
        activityDisplayMetrics.densityDpi = targetDensityDpi
    }
}
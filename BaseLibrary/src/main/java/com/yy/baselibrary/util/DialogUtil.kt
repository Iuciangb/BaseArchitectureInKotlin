package com.yy.baselibrary.util

import android.content.Context
import android.media.MediaCodec
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.yy.baselibrary.R

/**
 * @author YY
 * Created 2022/4/16
 * Description：
 */
class DialogUtil {
    private var mLoadingPopupWindow: PopupWindow? = null

    companion object {
        val instance: DialogUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DialogUtil()
        }
    }

    private fun initPopupWindow(context: Context) {
        if (mLoadingPopupWindow == null) {
            val view = LayoutInflater.from(context).inflate(R.layout.popup_window_loading, null)
            mLoadingPopupWindow = PopupWindow(
                view,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }

    fun showLoading(context: Context, parentView: ConstraintLayout) {
        initPopupWindow(context)
        mLoadingPopupWindow?.showAtLocation(parentView, Gravity.CENTER, 0, 0)
    }

    fun showLoading(context: Context, parentView: LinearLayout) {
        initPopupWindow(context)
        mLoadingPopupWindow?.showAtLocation(parentView, Gravity.CENTER, 0, 0)
    }

    fun showLoading(context: Context, parentView: RelativeLayout) {
        initPopupWindow(context)
        mLoadingPopupWindow?.showAtLocation(parentView, Gravity.CENTER, 0, 0)
    }

    fun showLoading(context: Context, parentView: View) {
        initPopupWindow(context)
        mLoadingPopupWindow?.showAtLocation(parentView, Gravity.CENTER, 0, 0)
    }

    fun dismissLoading() {
        if (mLoadingPopupWindow == null) {
            return
        }
        if (mLoadingPopupWindow?.isShowing!!) {
            mLoadingPopupWindow?.dismiss()
            mLoadingPopupWindow = null
        }
    }
}
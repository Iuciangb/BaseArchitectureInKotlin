package com.yy.basearchitectureinkotlin.api

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yy.basearchitectureinkotlin.R
import com.yy.basearchitectureinkotlin.Util.NetworkMonitoringUtil
import com.yy.baselibrary.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * @author YY
 * Created 2022/2/27
 * Description：
 */
open class BaseApiViewModel : ViewModel() {
    private val mJob = Job()

    companion object {
        private const val LOGGER_TAG = "/////"
        private const val SYMBOL_COLON = ":"
        private const val ERROR_EXCEPTION = "Api Exception"
    }

    val mCoroutineContext: CoroutineContext
        get() = mJob + Dispatchers.IO

    /**
     * 如果API錯誤則post這個value，外面呼叫API時需要錯誤處理就監聽這個值，不用則免
     */
    val apiError = MutableLiveData<String>()

    suspend fun <T> makeCall(
        context: Context?,
        result: HttpResult<T>?,
        mutableLiveData: MutableLiveData<HttpResult<T>>
    ) {
        makeCall(context, result, mutableLiveData, true)
    }

    suspend fun <T> makeCall(
        context: Context?,
        result: HttpResult<T>?,
        mutableLiveData: MutableLiveData<HttpResult<T>>,
        needError: Boolean
    ) {
        if (!NetworkMonitoringUtil.isNetworkAvailable(context ?: return)) {
            val message = context.getString(R.string.toast_please_check_network)
            apiError.postValue(message)
            Logger.e("error: $message")
            ApiErrorUtils.instance.showErrorMessage(context, message)
            return
        }
        if (result == null) {
            apiError.postValue(ERROR_EXCEPTION)
            Logger.e(ERROR_EXCEPTION)
            return
        }
        Logger.Companion.d(result.toString())
        if (result?.isSuccess!!) {
            mutableLiveData.postValue(result)
            return
        }
        val errorMessage = result?.msg
        apiError.postValue(errorMessage ?: "")
        if (!needError) {
            return
        }
        withContext(Dispatchers.Main) {
            Log.e(LOGGER_TAG, "error: $errorMessage")
            ApiErrorUtils.instance.showErrorMessage(context, errorMessage ?: "")
        }
    }

    fun handleApiError(message: String?) {
        Log.e(LOGGER_TAG, getTargetStackTraceElement().toString() + message)
    }

    private fun getTargetStackTraceElement(): StringBuilder? {
        // find the target invoked method
        val stackTraceMessage = StringBuilder()
        val stackTrace =
            Thread.currentThread().stackTrace
        for (stackTraceElement in stackTrace) {
            val className = stackTraceElement.className
            if (className.contains("com.yangfang.proxy_android")) {
                if (className.contains(LOGGER_TAG)) {
                    continue
                }
                stackTraceMessage.insert(
                    0,
                    className + SYMBOL_COLON + stackTraceElement.lineNumber + "\t" + stackTraceElement.methodName + "\n"
                )
            }
        }
        return stackTraceMessage
    }
}
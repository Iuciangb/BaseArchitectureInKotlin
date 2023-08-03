package com.yy.basearchitectureinkotlin.api

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.yy.baselibrary.util.ToastUtil

/**
 * @author YY
 * Created 2022/2/27
 * Description：
 */
class ApiErrorUtils private constructor() {
    private val TAG = this@ApiErrorUtils.javaClass.simpleName

    companion object {
        val instance: ApiErrorUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ApiErrorUtils()
        }
    }

    /**
     * 顯示錯誤訊息
     * */
    fun showErrorMessage(_context: Context?, message: String) {
        val context = _context as Activity?
        try {
            val jsonResponse = Gson().fromJson(message, JsonObject::class.javaObjectType)
            val error = jsonResponse.get("message").asJsonArray[0]
            val errorItem = Gson().fromJson(error, NewJsonError::class.javaObjectType)

            if (error != null) {
                //server error
                val errorMessage = errorItem.Message
                val errorCode = 400

                if (context == null) {
                    return
                }
                ToastUtil.showToast(context, message)
            } else {
                //server error but not json format
                ToastUtil.showToast(context, "")
            }

        } catch (e: Exception) {
            //app error
            if (message != "") {
                ToastUtil.showToast(context, message)
            }
        }
    }
}
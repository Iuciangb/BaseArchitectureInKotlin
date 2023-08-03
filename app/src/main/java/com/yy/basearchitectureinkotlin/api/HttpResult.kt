package com.yy.basearchitectureinkotlin.api

import com.google.gson.annotations.SerializedName

/**
 * @author YY
 * Created 2022/2/27
 * Description：API回傳的response格式，針對各自後端格式調整
 * The response format of the API will be adjusted to the respective backend format.
 */
class HttpResult<T>(
    @SerializedName("time") val time: Long = 0,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("msg") val msg: String = "",
    @SerializedName("data") val data: T,
) {
    companion object {
        private const val MSG_SUCCESS = "success"
    }

    val isSuccess: Boolean
        get() = msg == MSG_SUCCESS

    override fun toString(): String {
        return "HttpResult(time=$time, code=$code, msg='$msg', data=$data)"
    }
}
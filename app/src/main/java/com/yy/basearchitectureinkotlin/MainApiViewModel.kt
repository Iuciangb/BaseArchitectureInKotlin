package com.yy.basearchitectureinkotlin

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.yy.basearchitectureinkotlin.api.BaseApiViewModel
import com.yy.basearchitectureinkotlin.api.HttpClient
import com.yy.basearchitectureinkotlin.api.HttpResult
import com.yy.basearchitectureinkotlin.api.model.response.CheckYourself
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author YY
 * @create 2022/12/9
 * @Describe
 **/
class MainApiViewModel : BaseApiViewModel() {
    val checkYourself = MutableLiveData<HttpResult<List<CheckYourself>>>()

    /**
     * 獲取你自己的資料
     */
    fun getCheckYourselfList(context: Context) {
        CoroutineScope(mCoroutineContext).launch {
            try {
                val result = HttpClient.instance.getCheckYourselfList()
                makeCall(context, result, checkYourself)
            } catch (e: Exception) {
                handleApiError(e.message)
                makeCall(context, null, checkYourself)
            }
        }
    }
}
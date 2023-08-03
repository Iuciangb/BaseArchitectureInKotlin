package com.yy.basearchitectureinkotlin.api

import com.yy.basearchitectureinkotlin.Util.AppConstant
import com.yy.basearchitectureinkotlin.api.model.response.CheckYourself
import retrofit2.http.GET

/**
 * @author YY
 * Created 2022/2/27
 * Description：
 */
interface ApiService {

    /**
     * 獲取你自己的資料
     */
    @GET(AppConstant.Api.URL_CHECK_YOURSELF_LIST)
    suspend fun getCheckYourselfList(): HttpResult<List<CheckYourself>>
}
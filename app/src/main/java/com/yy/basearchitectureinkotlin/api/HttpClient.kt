package com.yy.basearchitectureinkotlin.api

import com.yy.basearchitectureinkotlin.BuildConfig
import com.yy.basearchitectureinkotlin.Util.AppConstant
import com.yy.basearchitectureinkotlin.api.model.response.CheckYourself
import com.yy.baselibrary.util.Logger
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author YY
 * Created 2022/2/27
 * Description：
 */
class HttpClient private constructor() {
    private var mOkHttpClient: OkHttpClient? = null
    private var mToken: String? = ""
    private var mApiService: ApiService

    companion object {
        val instance: HttpClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            HttpClient()
        }
    }

    init {
        if (mToken.isNullOrEmpty()) {
            mToken = ""
        }
        initOkHttpClient()
        mApiService = createApi()
    }

    fun setToken(token: String) {
        mToken = token
        initOkHttpClient()
        mApiService = createApi()
    }

    fun getToken(): String {
        //can return sp.getToken?:mToken.toString() like this
        return mToken.toString()
    }

    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private fun initOkHttpClient() {
        mOkHttpClient = run {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            OkHttpClient().newBuilder().connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(ApiInterceptor())
                .addInterceptor(BaseUrlChangingInterceptor.get())
                .authenticator(RefreshTokenAuthenticator())  //refresh jwt
                .build()
        }
    }

    private fun createApi(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_HOST)  // 设置服务器路径
            .client(mOkHttpClient!!)  // 设置okhttp的网络请求
            .addConverterFactory(GsonConverterFactory.create())// 添加转化库,默认是Gson
            .build()
        return retrofit.create(ApiService::class.java)
    }

    private inner class ApiInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            var token = getToken()

            Logger.d("token=$token")
            val request = original.newBuilder()
                .header(AppConstant.Key.AUTHORIZATION, token)
                .method(original.method, original.body).build()
            return chain.proceed(request)
        }
    }

    /**
     * 獲取你自己的資料
     */
    suspend fun getCheckYourselfList(): HttpResult<List<CheckYourself>> {
        return mApiService.getCheckYourselfList()
    }

    private fun generateMap(): LinkedHashMap<String, Any?> {
        var encryptMap = LinkedHashMap<String, Any?>()

        encryptMap["device_model"] = ""
        encryptMap["device_brand"] = ""
        return encryptMap
    }

    private fun toRequestBody(params: Map<String, Any?>): RequestBody {
        return JSONObject(params).toString()
            .toRequestBody("application/json;charset=utf-8".toMediaTypeOrNull())
    }
}
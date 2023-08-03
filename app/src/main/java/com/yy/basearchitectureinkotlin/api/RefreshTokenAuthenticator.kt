package com.yy.basearchitectureinkotlin.api

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * @author YY
 * Created 2022/2/27
 * Description：更新JWT TOKEN用
 */
class RefreshTokenAuthenticator : Authenticator {

    //    //params key
//    val REFRESH_TOKEN = "refreshToken"
//    val TOKEN = "Token"
//    val SIGN = "Sign"
//
//    private var api: ApiService
//    var okHttpClient: OkHttpClient
//
//    companion object {
//        val instance = RefreshTokenAuthenticator()
//    }
//
//    constructor() {
//        okHttpClient = OkHttpClient().newBuilder().connectTimeout(100, TimeUnit.SECONDS)
//            .readTimeout(100, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS)
//            .addInterceptor(
//                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
//            )
//            .addInterceptor { chain ->
//                val original = chain.request()
//                val request =
//                    original.newBuilder().method(original.method(), original.body()).build()
//                chain.proceed(request)
//            }.build()
//        val retrofitAuth = Retrofit.Builder().baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
//        api = retrofitAuth.create<GpkGamesApi>(GpkGamesApi::class.java)
//    }
//
    override fun authenticate(route: Route?, response: Response): Request? {
//
//        val validateString =
//            (LoginModel.instance.refreshToken + LoginModel.instance.jwtToken).toLowerCase()
//        val sign = Md5HashUtils.instance.md5(validateString + BuildConfig.AUTH_APIKEY)
//
//        val params = JsonObject()
//        params.addProperty(TOKEN, LoginModel.instance.jwtToken)
//        params.addProperty(REFRESH_TOKEN, LoginModel.instance.refreshToken)
//        params.addProperty(SIGN, sign)
//        val body = RequestBody.create(
//            MediaType.parse("application/json;charset=utf-8"),
//            params.toString()
//        )
//
//        val newJwt = api.refreshToken(body).execute().body() ?: return null
//        return if (updateJWT(newJwt!!.string())) {
//            Log.v("JWT", "get new jwt ${LoginModel.instance.jwtToken}")
//            response.request().newBuilder()
//                .header("Authorization", "Bearer " + LoginModel.instance.jwtToken).build()
//        } else {
//            println("登入令牌已失效，请重新登入")
//            val PREFS_FILENAME = "com.gpk.games.prefs.loginModel"
//            val prefs: SharedPreferences =
//                AppModel.instance.applicationContext.getSharedPreferences(PREFS_FILENAME, 0)
//            prefs.edit().putString("JwtToken", "").apply()
//            AppModel.instance.setPref(RecentWatchModel.RECENT_WATCH_LIST, "") //最近瀏覽緩存清空
//            null
//        }
        return null //這行暫時加的，要刪
    }
//
//    fun updateJWT(result: String): Boolean {
//        val resultJson = JSONObject(result)
//        return if (resultJson.getInt("Code") == 200) {
//            val result = resultJson.getJSONObject("Result")
//            val refreshToken = result.getString("RefreshToken")
//            val newJWT = result.getString("JwtToken")
//            LoginModel.instance.refreshToken = refreshToken
//            LoginModel.instance.jwtToken = newJWT
//            true
//        } else {
//            false
//        }
//    }
}
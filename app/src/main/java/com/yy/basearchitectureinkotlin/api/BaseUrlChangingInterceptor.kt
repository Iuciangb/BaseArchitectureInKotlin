package com.yy.basearchitectureinkotlin.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @author YY
 * Created 2022/2/27
 * Description：
 */
class BaseUrlChangingInterceptor private constructor() : Interceptor {

    companion object {
        private var sInterceptor: BaseUrlChangingInterceptor? = null

        fun get(): BaseUrlChangingInterceptor {
            if (sInterceptor == null) {
                sInterceptor = BaseUrlChangingInterceptor()
            }
            return sInterceptor!!
        }
    }

    private var scheme:String? = null
    private var host:String? = null
    private var port = 0

    fun setInterceptor(scheme: String, host: String, port: Int) {
        this.scheme = scheme
        this.host = host
        this.port = port
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()

        if (scheme != null && host != null)
        {
            var newUrl = HttpUrl.Builder()
                .scheme(scheme!!)
                .host(host!!)
                .port(port)
                .encodedQuery(original.url.encodedQuery)
                .encodedFragment(original.url.encodedFragment)
                .addPathSegments(original.url.pathSegments.reduce { acc, s -> "$acc/$s" })
                .build()

            original = original.newBuilder()
                .url(newUrl)
                .build()
        }
        return chain.proceed(original)
    }
}
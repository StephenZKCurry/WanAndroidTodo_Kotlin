package com.zk.wanandroidtodo.manager

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.zk.wanandroidtodo.BuildConfig
import com.zk.wanandroidtodo.api.ApiService
import com.zk.wanandroidtodo.base.MyApplication
import com.zk.wanandroidtodo.utils.Constant
import com.zk.wanandroidtodo.utils.NetworkUtils
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @description:     管理Retrofit
 * @author:         zhukai
 * @date:     2018/8/31 11:28
 */
object RetrofitManager {

    val service: ApiService by lazy { createApi(ApiService::class.java, Constant.BASE_URL) }

    /**
     * 缓存机制
     * 在响应请求之后在 data/data/<包名>/cache 下建立一个response 文件夹，保持缓存数据。
     * 这样我们就可以在请求的时候，如果判断到没有网络，自动读取缓存的数据。
     * 同样这也可以实现，在我们没有网络的情况下，重新打开App可以浏览的之前显示过的内容。
     * 也就是：判断网络，有网络，则从网络获取，并保存到缓存中，无网络，则从缓存中获取。
     * https://werb.github.io/2016/07/29/%E4%BD%BF%E7%94%A8Retrofit2+OkHttp3%E5%AE%9E%E7%8E%B0%E7%BC%93%E5%AD%98%E5%A4%84%E7%90%86/
     */
    private val cacheControlInterceptor by lazy {
        Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtils.isNetworkAvailable(MyApplication.mContext)) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            }

            val originalResponse = chain.proceed(request)
            if (NetworkUtils.isNetworkAvailable(MyApplication.mContext)) {
                // 有网络时 设置缓存为默认值
                val cacheControl = request.cacheControl().toString()
                originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build()
            } else {
                // 无网络时 设置超时为1周
                val maxStale = 60 * 60 * 24 * 7
                originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .removeHeader("Pragma")
                        .build()
            }
        }
    }

    /**
     * 创建api
     */
    fun <T> createApi(clazz: Class<T>, url: String): T {
        // 指定缓存路径,缓存大小 50Mb
        val cache = Cache(File(MyApplication.mContext.getCacheDir(), "HttpCache"),
                (1024 * 1024 * 50).toLong())
        // Cookie 持久化
        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(MyApplication.mContext))

        val builder = OkHttpClient.Builder().run {
            cookieJar(cookieJar)
            cache(cache)
            addInterceptor(cacheControlInterceptor)
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(15, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            // Log 拦截器
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            }
            addInterceptor(httpLoggingInterceptor)
        }

        return Retrofit.Builder().run {
            baseUrl(url)
            client(builder.build())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create())
            build()
        }.create(clazz)
    }
}
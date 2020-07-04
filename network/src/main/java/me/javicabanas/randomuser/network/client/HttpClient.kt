package me.javicabanas.randomuser.network.client

import me.javicabanas.randomuser.network.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

private const val DEFAULT_TIMEOUT_SECONDS: Long = 20
private const val WEBSOCKET_PING_INTERVAL_SECONDS: Long = 10
private const val DEFAULT_READ_TIMEOUT_SECONDS: Long = 45
private const val NO_TIMEOUT_SECONDS: Long = 0

internal object HttpClient {
    private val logInterceptor by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    val okClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NO_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .pingInterval(WEBSOCKET_PING_INTERVAL_SECONDS, TimeUnit.SECONDS)
            .addLoggingInterceptor(logInterceptor)
            .build()
    }

    private fun OkHttpClient.Builder.addLoggingInterceptor(loggingInterceptor: HttpLoggingInterceptor) =
        apply { if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor) }
}

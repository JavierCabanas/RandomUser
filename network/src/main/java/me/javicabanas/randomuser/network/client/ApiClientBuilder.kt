package me.javicabanas.randomuser.network.client

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import kotlin.reflect.KClass

class ApiClientBuilder(baseUrl: String, httpClient: HttpClient) {
    private val contentType = "application/json".toMediaType()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient.okClient)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()

    fun <T : Any> buildEndpoint(apiClass: KClass<T>): T {
        return retrofit.create(apiClass.java)
    }
}

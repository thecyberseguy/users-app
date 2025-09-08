package thecyberseguy.usersapp.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    companion object {
        private const val CONTENT_TYPE = "Content-Type"
        private const val CONTENT_TYPE_VALUE = "application/json"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE)
            .method(chain.request().method, chain.request().body)
            .build()

        return chain.proceed(newRequest)
    }

}
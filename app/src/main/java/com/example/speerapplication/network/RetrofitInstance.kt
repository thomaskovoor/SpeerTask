package com.example.speerapplication.network

import com.itkacher.okprofiler.BuildConfig
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * The `RetrofitInstance` class is used to create a singleton instance of Retrofit.
 * @property getRetrofitInstance This function returns a singleton instance of Retrofit. If the instance doesn't exist, it creates one.
 * It first creates an OkHttpClient, which will be used by Retrofit to make network requests. The OkHttpClient is configured with a connection timeout of 1 minute and a read timeout of 60 seconds.
 * If the build type is debug, an OkHttpProfilerInterceptor is added to the OkHttpClient. This interceptor is a tool for profiling OkHttp network requests and responses.
 * The base URL for the Retrofit instance is set to "https://api.github.com/users/".
 * The GsonConverterFactory is added to the Retrofit instance. This factory is used to generate an implementation of the `ApiInterface` interface using Gson for conversion.
 *
 * The instance is created inside a companion object to ensure that it's a singleton. This means that there will only be one instance of Retrofit in the entire application, which is efficient for resources.
 */

class RetrofitInstance {
    companion object{

        fun getRetrofitInstance(): Retrofit {
            val builder = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
            // .writeTimeout(60,TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(OkHttpProfilerInterceptor())
            }
            val client = builder.build()
            val baseURL = "https://api.github.com/users/"

            return Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }
    }
}
package com.livingcode.test.celotest.storage.network

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {
    @GET("/api")
    fun getUsers(
        @Query("page") page: Int,
        @Query("results") results: Int = 50,
        @Query("seed") seed: String = "foobar"
    ): Call<RandomUserApiResponse>

    companion object {
        private const val BASE_URL = "https://randomuser.me/"

        fun create(): RandomUserApi = create(HttpUrl.parse(BASE_URL)!!)

        private fun create(url: HttpUrl): RandomUserApi {
            val client = OkHttpClient.Builder().build()

            return Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RandomUserApi::class.java)
        }
    }
}
package com.livingcode.test.celotest.storage

import android.util.Log
import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.livingcode.test.celotest.storage.models.User
import com.livingcode.test.celotest.storage.network.RandomUserApi
import com.livingcode.test.celotest.storage.network.RandomUserApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserBoundaryCallback(
    private val apiClient: RandomUserApi,
    private val handleResponse: (List<User>?) -> Unit
) : PagedList.BoundaryCallback<User>() {
    private val apiCallback = object : Callback<RandomUserApiResponse> {
        override fun onFailure(call: Call<RandomUserApiResponse>?, t: Throwable?) {
            Log.e("TEST", "Failed: " + call?.request()?.url())
        }

        override fun onResponse(
            call: Call<RandomUserApiResponse>?,
            response: Response<RandomUserApiResponse>?
        ) {
            handleApiResponse(response)
        }
    }
    private var lastPage = 0

    @MainThread
    override fun onZeroItemsLoaded() {
        apiClient.getUsers(lastPage + 1).enqueue(apiCallback)
    }

    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: User) {
        apiClient.getUsers(lastPage + 1).enqueue(apiCallback)
    }

    private fun handleApiResponse(response: Response<RandomUserApiResponse>?) {
        val page = response?.body()?.info?.page ?: 0
        if (page != lastPage) {
            lastPage = page
            handleResponse(response?.body()?.results)
        }
    }
}


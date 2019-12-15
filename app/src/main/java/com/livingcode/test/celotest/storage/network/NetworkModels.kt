package com.livingcode.test.celotest.storage.network

import com.livingcode.test.celotest.storage.models.User

data class RandomUserApiResponse(
    val info: Info,
    val results: List<User>
)

data class Info(
    val page: Int,
    val results: Int,
    val seed: String,
    val version: String
)
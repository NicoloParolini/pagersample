package com.livingcode.test.celotest.storage

import androidx.paging.PagedList
import androidx.paging.toObservable
import com.livingcode.test.celotest.storage.database.UserDatabase
import com.livingcode.test.celotest.storage.models.User
import com.livingcode.test.celotest.storage.network.RandomUserApi
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class UserRepository constructor(
    private val db: UserDatabase
) {
    private val boundaryCallback = UserBoundaryCallback(
        RandomUserApi.create(),
        this::storeUsers
    )

    fun getAllUsers(): Observable<PagedList<User>> {
        return db.userDao().getAllUsers()
            .toObservable(pageSize = 50, boundaryCallback = boundaryCallback)
    }

    fun getFilteredUsers(query: String): Observable<PagedList<User>> {
        return db.userDao().getFilteredUsers("%$query%")
            .toObservable(pageSize = 50, boundaryCallback = boundaryCallback)
    }

    private fun storeUsers(users: List<User>?) {
        users?.let { db.userDao().insert(users).subscribeOn(Schedulers.io()).subscribe() }
    }
}

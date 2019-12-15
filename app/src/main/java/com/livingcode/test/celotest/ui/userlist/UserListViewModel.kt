package com.livingcode.test.celotest.ui.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.livingcode.test.celotest.storage.UserRepository
import com.livingcode.test.celotest.storage.models.User
import com.livingcode.test.celotest.ui.CmdOpenUserDetails
import com.livingcode.test.celotest.ui.NavigationCommand
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class UserListViewModel(private val repository: UserRepository) : ViewModel() {
    val userList: Observable<PagedList<User>> = repository.getAllUsers()
    val selectedUser: BehaviorSubject<User> = BehaviorSubject.create()
    val navigation: PublishSubject<NavigationCommand> = PublishSubject.create()

    fun selectUser(user: User) {
        selectedUser.onNext(user)
        navigation.onNext(CmdOpenUserDetails)
    }

    fun searchUser(query: String): Observable<PagedList<User>> {
        return repository.getFilteredUsers(query)
    }
}

class UserListViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return UserListViewModel(repository) as T
    }
}
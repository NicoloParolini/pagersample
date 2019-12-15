package com.livingcode.test.celotest.ui.userlist

import androidx.paging.PagedList
import com.livingcode.test.celotest.storage.UserRepository
import com.livingcode.test.celotest.storage.models.User
import com.livingcode.test.celotest.ui.CmdOpenUserDetails
import com.livingcode.test.celotest.ui.NavigationCommand
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class UserListViewModelTest {
    private lateinit var viewModel: UserListViewModel

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var filteredUsers: PagedList<User>

    @MockK
    private lateinit var unfilteredUsers: PagedList<User>

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { userRepository.getAllUsers() } returns Observable.just(unfilteredUsers)
        every { userRepository.getFilteredUsers(any()) } returns Observable.just(filteredUsers)
        viewModel = UserListViewModel(userRepository)
    }

    @Test
    fun selectUserCallsNavigation() {
        val testObserver = TestObserver<NavigationCommand>()
        viewModel.navigation.subscribe(testObserver)
        val testUser = User(0)
        viewModel.selectUser(testUser)
        testObserver.assertValueCount(1)
        testObserver.assertValue(CmdOpenUserDetails)
    }

    @Test
    fun selectUserReturnsCorrectUser() {
        val testObserver = TestObserver<User>()
        viewModel.selectedUser.subscribe(testObserver)
        val testUser = User(0)
        viewModel.selectUser(testUser)
        testObserver.assertValueCount(1)
        testObserver.assertValue(testUser)
    }

    @Test
    fun searchUserReturnsCorrectList() {
        val testObserver = TestObserver<PagedList<User>>()
        viewModel.searchUser("any").subscribe(testObserver)
        testObserver.assertValueCount(1)
        testObserver.assertValue(filteredUsers)
    }

    @Test
    fun userListReturnsCorrectListOnSubscribe() {
        val testObserver = TestObserver<PagedList<User>>()
        viewModel.userList.subscribe(testObserver)
        testObserver.assertValueCount(1)
        testObserver.assertValue(unfilteredUsers)
    }

    @Test
    fun navigationReturnsNothingOnSubscribe() {
        val testObserver = TestObserver<NavigationCommand>()
        viewModel.navigation.subscribe(testObserver)
        testObserver.assertNotComplete()
        testObserver.assertNoErrors()
        testObserver.assertNoValues()
        testObserver.assertNotTerminated()
    }

    @Test
    fun selectedUserReturnsNothingOnSubscribe() {
        val testObserver = TestObserver<User>()
        viewModel.selectedUser.subscribe(testObserver)
        testObserver.assertNotComplete()
        testObserver.assertNoErrors()
        testObserver.assertNoValues()
        testObserver.assertNotTerminated()
    }
}
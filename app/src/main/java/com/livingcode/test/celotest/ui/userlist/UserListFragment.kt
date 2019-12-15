package com.livingcode.test.celotest.ui.userlist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.livingcode.test.celotest.MainActivity
import com.livingcode.test.celotest.databinding.ListUserFragmentBinding
import com.livingcode.test.celotest.storage.UserRepository
import com.livingcode.test.celotest.storage.database.UserDatabase
import com.livingcode.test.celotest.ui.CmdOpenUserDetails
import com.livingcode.test.celotest.ui.userdetails.UserDetailsFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class UserListFragment : Fragment() {
    private val disposable = CompositeDisposable()
    private lateinit var adapter: UserListAdapter
    private lateinit var binding: ListUserFragmentBinding
    private var userDisposable: Disposable? = null

    companion object {
        fun newInstance() = UserListFragment()
    }

    private lateinit var viewModel: UserListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListUserFragmentBinding.inflate(inflater)
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.w("TEST", "search: " + s)
                userDisposable?.dispose()
                if (s.isNullOrEmpty()) {
                    userDisposable = viewModel.userList
                        .subscribe { list ->
                            adapter.submitList(list)
                        }
                } else {
                    userDisposable = viewModel.searchUser(s.toString())
                        .subscribe { users ->
                            adapter.submitList(users)
                        }
                }
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // do nothing
            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val db = UserDatabase.create(requireActivity(), false)
        val factory = UserListViewModelFactory(UserRepository(db))
        viewModel = ViewModelProviders.of(this, factory).get(UserListViewModel::class.java)
        adapter = UserListAdapter(viewModel)
        binding.userlist.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        userDisposable = viewModel.userList
            .subscribe { list ->
                adapter.submitList(list)
            }
        disposable.add(viewModel.navigation.subscribe { cmd ->
            when (cmd) {
                CmdOpenUserDetails -> openUserDetails()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
        userDisposable?.dispose()
    }

    private fun openUserDetails() {
        (activity as MainActivity).navigateTo(UserDetailsFragment.newInstance(viewModel))
    }
}

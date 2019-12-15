package com.livingcode.test.celotest.ui.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.livingcode.test.celotest.R
import com.livingcode.test.celotest.databinding.UserDetailsFragmentBinding
import com.livingcode.test.celotest.storage.models.User
import com.livingcode.test.celotest.ui.userlist.UserListViewModel
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable

class UserDetailsFragment(
    private val viewModel: UserListViewModel
) : Fragment() {
    private val disposable = CompositeDisposable()
    private val adapter = UserDetailsAdapter()
    private lateinit var binding: UserDetailsFragmentBinding

    companion object {
        fun newInstance(
            viewModel: UserListViewModel
        ) = UserDetailsFragment(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserDetailsFragmentBinding.inflate(inflater)
        binding.userDetails.adapter = adapter
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        disposable.add(viewModel.selectedUser
            .subscribe { user ->
                Picasso.get()
                    .load(user.picture?.large)
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder)
                    .into(binding.userImage)
                adapter.submitList(buildDetails(user))
            })
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    private fun buildDetails(user: User): List<String> {
        return listOfNotNull(
            user.name?.title,
            user.name?.first,
            user.name?.last,
            user.gender,
            user.nat,
            user.phone,
            user.location?.street?.number.toString(),
            user.location?.street?.name,
            user.location?.city,
            user.location?.state,
            user.location?.postcode,
            user.dob?.date,
            user.dob?.age.toString(),
            user.cell
        )

    }
}
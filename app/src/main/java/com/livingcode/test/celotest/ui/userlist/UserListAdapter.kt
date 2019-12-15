package com.livingcode.test.celotest.ui.userlist

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.livingcode.test.celotest.R
import com.livingcode.test.celotest.databinding.RowUserBinding
import com.livingcode.test.celotest.storage.models.User
import com.squareup.picasso.Picasso


class UserListAdapter(private val viewModel: UserListViewModel) :
    PagedListAdapter<User, UserItemViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object :
            DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(
                old: User,
                new: User
            ) = old == new

            override fun areContentsTheSame(
                old: User,
                new: User
            ) = old == new
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val binding = RowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserItemViewHolder(binding, parent.context.resources)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        val user: User? = getItem(position)
        user?.let { holder.bindTo(user, viewModel) }
    }
}

class UserItemViewHolder(private val binding: RowUserBinding, private val resources: Resources) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindTo(user: User, viewModel: UserListViewModel) {
        binding.userDob.text = user.dob?.date
        binding.userGender.text = user.gender
        binding.userName.text = resources.getString(
            R.string.user_name,
            user.name?.title,
            user.name?.first,
            user.name?.last
        )
        Picasso.get()
            .load(user.picture?.thumbnail)
            .placeholder(R.drawable.user_thumb_placeholder)
            .error(R.drawable.user_thumb_placeholder)
            .into(binding.userThumbnail)
        binding.root.setOnClickListener { viewModel.selectUser(user) }
    }
}
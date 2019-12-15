package com.livingcode.test.celotest.ui.userdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.livingcode.test.celotest.databinding.RowUserDetailBinding

class UserDetailsAdapter : ListAdapter<String, UserDetailViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object :
            DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                old: String,
                new: String
            ) = old == new

            override fun areContentsTheSame(
                old: String,
                new: String
            ) = old == new
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDetailViewHolder {
        val binding =
            RowUserDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserDetailViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    fun update(details: List<String>) {
        submitList(details)
    }
}

class UserDetailViewHolder(private val binding: RowUserDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindTo(detail: String) {
        binding.userDetailText.text = detail
    }
}
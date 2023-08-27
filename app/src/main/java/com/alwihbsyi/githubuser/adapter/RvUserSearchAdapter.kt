package com.alwihbsyi.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alwihbsyi.githubuser.data.response.ItemsItem
import com.alwihbsyi.githubuser.databinding.ItemUserLayoutBinding
import com.bumptech.glide.Glide

class RvUserSearchAdapter: ListAdapter<ItemsItem, RvUserSearchAdapter.RvUserSearchViewHolder>(DIFF_CALLBACK) {

    inner class RvUserSearchViewHolder(val binding: ItemUserLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ItemsItem) {
            binding.tvUsername.text = data.login
            Glide.with(itemView.context).load(data.avatarUrl).into(binding.ivUserProfile)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvUserSearchViewHolder {
        return RvUserSearchViewHolder(
            ItemUserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RvUserSearchViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            onClick?.invoke(data)
        }
    }

    var onClick: ((ItemsItem) -> Unit)? = null

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}
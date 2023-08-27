package com.alwihbsyi.githubuser.adapter

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alwihbsyi.githubuser.data.response.ResponseItem
import com.alwihbsyi.githubuser.databinding.ItemUserLayoutBinding
import com.bumptech.glide.Glide

class RvUserAdapter: ListAdapter<ResponseItem, RvUserAdapter.RvUserViewHolder>(DIFF_CALLBACK) {

    inner class RvUserViewHolder(val binding: ItemUserLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ResponseItem) {
            binding.tvUsername.text = data.login
            Glide.with(itemView.context).load(data.avatarUrl).into(binding.ivUserProfile)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvUserViewHolder {
        return RvUserViewHolder(
            ItemUserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RvUserViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            onClick?.invoke(data)
        }
    }

    var onClick: ((ResponseItem) -> Unit)? = null

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResponseItem>() {
            override fun areItemsTheSame(
                oldItem: ResponseItem,
                newItem: ResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResponseItem,
                newItem: ResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}
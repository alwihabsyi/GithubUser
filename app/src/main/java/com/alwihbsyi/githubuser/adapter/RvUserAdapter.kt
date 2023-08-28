package com.alwihbsyi.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alwihbsyi.githubuser.data.response.UserResponse
import com.alwihbsyi.githubuser.databinding.ItemUserLayoutBinding
import com.bumptech.glide.Glide

class RvUserAdapter : RecyclerView.Adapter<RvUserAdapter.RvUserViewHolder>() {

    inner class RvUserViewHolder(val binding: ItemUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserResponse) {
            binding.tvUsername.text = data.login
            Glide.with(itemView.context).load(data.avatarUrl).into(binding.ivUserProfile)
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<UserResponse>() {
        override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvUserViewHolder {
        return RvUserViewHolder(
            ItemUserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: RvUserViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick?.invoke(item)
        }
    }

    var onClick: ((UserResponse) -> Unit)? = null

}
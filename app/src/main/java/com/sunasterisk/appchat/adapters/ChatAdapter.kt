package com.sunasterisk.appchat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.sunasterisk.appchat.base.BaseAdapter
import com.sunasterisk.appchat.base.BaseViewHolder
import com.sunasterisk.appchat.databinding.ItemChatBinding
import com.sunasterisk.appchat.db.entity.Chat

class ChatAdapter(
    private val onItemClick: (chat: Chat, pos: Int) -> Unit,
) : BaseAdapter<Chat, ChatAdapter.ViewHolder>(ChatDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent, onItemClick)

    class ViewHolder(
        val binding: ItemChatBinding,
        listener: (chat: Chat, pos: Int) -> Unit
    ) : BaseViewHolder<Chat>(binding, listener) {

        override val itemData: Chat?
            get() = binding.chat

        override fun bindData(item: Chat) {
            binding.chat = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, onItemClick: (chat: Chat, pos: Int) -> Unit): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemChatBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, onItemClick)
            }
        }
    }
}

object ChatDiffCallback : DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat) =
        oldItem.chatId == newItem.chatId

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat) =
        oldItem == newItem
}

package com.sunasterisk.appchat.ui.chat

import com.sunasterisk.appchat.R
import com.sunasterisk.appchat.adapters.ChatAdapter
import com.sunasterisk.appchat.base.BaseFragment
import com.sunasterisk.appchat.db.entity.Chat
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : BaseFragment() {

    override val layoutResource get() = R.layout.fragment_chat

    private val onItemClick: (chat: Chat, pos: Int) -> Unit = { chat, pos ->
        onItemClick(chat, pos)
    }

    override fun initComponents() {
        val adapter = ChatAdapter(onItemClick)
        recyclerViewChatList.adapter = adapter
    }

    override fun initListeners() = Unit

    private fun onItemClick(chat: Chat, pos: Int) {}
}

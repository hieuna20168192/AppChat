package com.sunasterisk.appchat.ui.chat

import com.sunasterisk.appchat.R
import com.sunasterisk.appchat.adapters.ChatAdapter
import com.sunasterisk.appchat.base.BaseBindingFragment
import com.sunasterisk.appchat.databinding.FragmentChatBinding
import com.sunasterisk.appchat.db.entity.Chat
import com.sunasterisk.appchat.utils.DebounceQueryTextListener
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class ChatFragment : BaseBindingFragment<FragmentChatBinding>() {

    override val layoutResource get() = R.layout.fragment_chat
    private val chatViewModel: ChatViewModel by viewModel()

    private val onItemClick: (chat: Chat, pos: Int) -> Unit = { chat, pos ->
        onItemClick(chat, pos)
    }

    override fun initComponents() {
        binding.chatViewModel = chatViewModel
        binding.lifecycleOwner = this
        val adapter = ChatAdapter(onItemClick)
        recyclerViewChatList.adapter = adapter
    }

    override fun initListeners() {
        searchViewChats.setOnQueryTextListener(
            DebounceQueryTextListener(
                this.lifecycle
            ) { query ->
                chatViewModel.offerSearchQuery(query)
            }
        )
    }

    private fun onItemClick(chat: Chat, pos: Int) {}
}

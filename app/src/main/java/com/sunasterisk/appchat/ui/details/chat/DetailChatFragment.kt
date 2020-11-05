package com.sunasterisk.appchat.ui.details.chat

import com.sunasterisk.appchat.R
import com.sunasterisk.appchat.base.BaseBindingFragment
import com.sunasterisk.appchat.databinding.FragmentDetailChatBinding

class DetailChatFragment : BaseBindingFragment<FragmentDetailChatBinding>() {

    override val layoutResource get() = R.layout.fragment_detail_chat

    override fun initComponents() {
        binding.lifecycleOwner = this
    }

    override fun initListeners() = Unit
}

package com.sunasterisk.appchat.db.firebase

import android.content.res.Resources
import com.sunasterisk.appchat.App
import com.sunasterisk.appchat.R

object RemoteConstant {

    const val COLLECTION_USER = "User"
    const val COLLECTION_CHAT = "Chat"
    const val COLLECTION_GROUP = "Group"
    const val COLLECTION_RECENT_MESSAGE_CHAT = "recentMessage"
    const val DEBOUNCE_PERIOD = 500L
    private val resources: Resources = App.getContext().resources

    const val MSG_USER_HAS_BEEN_EXISTED = "User has been registered!"

    fun getLastMessageId(chatId: String) =
        resources.getQuantityString(
            R.plurals.last_message_chat_id,
            resources.getInteger(R.integer.integer_1),
            chatId,
        )
}

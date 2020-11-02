package com.sunasterisk.appchat.db.firebase

import android.content.res.Resources
import com.sunasterisk.appchat.App
import com.sunasterisk.appchat.R

object RemoteConstant {

    const val EMAIL_NOT_VERIFIED = "email_not_verified"
    const val MSG_EMAIL_NOT_VERIFIED = "Email is not verified"
    const val COLLECTION_USER = "User"
    const val COLLECTION_CHAT = "Chat"
    const val COLLECTION_GROUP = "Group"
    const val COLLECTION_RECENT_MESSAGE_CHAT = "recentMessage"

    private val resources: Resources = App.getContext().resources

    const val MSG_USER_HAS_BEEN_EXISTED = "User has been registered!"

    fun getLastMessageId(chatId: String) =
        resources.getQuantityString(
            R.plurals.last_message_chat_id,
            resources.getInteger(R.integer.integer_1),
            chatId,
        )
}

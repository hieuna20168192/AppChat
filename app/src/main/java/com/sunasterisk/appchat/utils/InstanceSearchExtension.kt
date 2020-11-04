package com.sunasterisk.appchat.utils

import com.sunasterisk.appchat.db.entity.Chat

fun List<Chat>.query(
    query: String?,
): List<Chat> {
    if (query.isNullOrEmpty()) return this
    val chatsResult = mutableListOf<Chat>()
    forEach { chat ->
        chat.recentMessage?.let { lastMessage ->
            if (lastMessage.fromUser.contains(query) ||
                lastMessage.message.contains(query)
            ) {
                chatsResult.add(chat)
            }
        }
    }
    return chatsResult
}

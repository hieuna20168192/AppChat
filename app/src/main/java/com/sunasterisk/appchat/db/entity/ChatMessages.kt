package com.sunasterisk.appchat.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ChatMessages(
    @Embedded
    val chat: Chat,
    @Relation(
        parentColumn = "chat_id",
        entityColumn = "from_chat_id",
    )
    val chatMessages: List<Message>
)

package com.sunasterisk.appchat.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithChats(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "chat_id",
        associateBy = Junction(UserChatCrossRef::class)
    )
    val chats: List<Chat>
)

data class ChatWithUsers(
    @Embedded val chat: Chat,
    @Relation(
        parentColumn = "chat_id",
        entityColumn = "user_id",
        associateBy = Junction(UserChatCrossRef::class)
    )
    val users: List<User>
)

package com.sunasterisk.appchat.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["user_id", "chat_id"])
data class UserChatCrossRef(
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "chat_id")
    val chatId: String,
)

package com.sunasterisk.appchat.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class Message(
    @PrimaryKey
    @ColumnInfo(name = "message_id")
    val messageId: String = "",
    @ColumnInfo(name = "message_type")
    val messageType: String = "",
    @ColumnInfo(name = "chat_type")
    val chatType: String = "",
    @ColumnInfo(name = "content_type")
    val contentType: String = "",
    val file: String = "",
    @ColumnInfo(name = "from_id")
    val fromId: String = "",
    @ColumnInfo(name = "image_url")
    val imageUrl: String = "",
    val message: String = "",
    val status: Int = 0,
    val date: Date?,
    @ColumnInfo(name = "to_id")
    val toId: String = "",
    val video: String = "",
    @ColumnInfo(name = "from_chat_id")
    val fromChatId: String,
    @ColumnInfo(name = "from_group_id")
    val fromGroupId: String,
) : Parcelable

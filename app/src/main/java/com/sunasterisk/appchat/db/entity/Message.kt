package com.sunasterisk.appchat.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
@Entity
data class Message(
    @PrimaryKey
    @ColumnInfo(name = "message_id")
    var messageId: String = "",
    @ColumnInfo(name = "message_type")
    var messageType: String = "",
    @ColumnInfo(name = "chat_type")
    var chatType: String = "",
    @ColumnInfo(name = "content_type")
    var contentType: String = "",
    var file: String = "",
    @ColumnInfo(name = "from_id")
    var fromId: String = "",
    @ColumnInfo(name = "image_url")
    var imageUrl: String = "",
    var message: String = "",
    var status: Int = 0,
    var date: Date? = null,
    @ColumnInfo(name = "to_id")
    var toId: String = "",
    var video: String = "",
    @ColumnInfo(name = "from_chat_id")
    var fromChatId: String = "",
    @ColumnInfo(name = "from_group_id")
    var fromGroupId: String = "",
) : Parcelable

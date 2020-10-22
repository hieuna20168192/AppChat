package com.sunasterisk.appchat.db.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Chat.TABLE_NAME)
data class Chat(
    @PrimaryKey
    @ColumnInfo(name = "chat_id")
    val chatId: String = "",
    @Ignore
    val memberIds: List<String> = emptyList(),
    @ColumnInfo(name = "last_message")
    @Embedded
    val recentMessage: Message
) : Parcelable {

    companion object {
        const val TABLE_NAME = "Chat"
    }
}

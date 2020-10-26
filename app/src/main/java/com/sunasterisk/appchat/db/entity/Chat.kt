package com.sunasterisk.appchat.db.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Chat.TABLE_NAME)
data class Chat(
    @PrimaryKey
    @ColumnInfo(name = "chat_id")
    var chatId: String = "",
    @Ignore
    val memberIds: List<String> = emptyList(),
    @Embedded
    var recentMessage: Message? = null
) : Parcelable {

    companion object {
        const val TABLE_NAME = "Chat"
    }
}

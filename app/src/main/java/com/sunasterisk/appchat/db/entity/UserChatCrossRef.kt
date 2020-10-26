package com.sunasterisk.appchat.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(primaryKeys = ["user_id", "chat_id"])
@Parcelize
data class UserChatCrossRef(
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "chat_id")
    val chatId: String,
) : Parcelable

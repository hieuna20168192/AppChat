package com.sunasterisk.appchat.db.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = Group.TABLE_NAME)
data class Group(
    @PrimaryKey
    @ColumnInfo(name = "group_id")
    val groupId: String = "",
    @ColumnInfo(name = "admin_id")
    val adminId: String = "",
    @ColumnInfo(name = "admin_name")
    val adminName: String = "",
    @ColumnInfo(name = "created_at")
    val createdAt: Date?,
    @ColumnInfo(name = "group_icon")
    val groupIcon: String = "",
    @Ignore
    val memberIds: List<String> = emptyList(),
    val name: String = "",
    @ColumnInfo(name = "last_message")
    @Embedded
    val recentMessage: Message
) : Parcelable {

    companion object {
        const val TABLE_NAME = "Group"
    }
}

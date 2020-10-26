package com.sunasterisk.appchat.db.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = Group.TABLE_NAME)
data class Group(
    @PrimaryKey
    @ColumnInfo(name = "group_id")
    var groupId: String = "",
    @ColumnInfo(name = "admin_id")
    var adminId: String = "",
    @ColumnInfo(name = "admin_name")
    var adminName: String = "",
    @ColumnInfo(name = "created_at")
    var createdAt: Date? = null,
    @ColumnInfo(name = "group_icon")
    var groupIcon: String = "",
    @Ignore
    val memberIds: List<String> = emptyList(),
    var name: String = "",
    var description: String = "",
) : Parcelable {

    companion object {
        const val TABLE_NAME = "Group"
    }
}

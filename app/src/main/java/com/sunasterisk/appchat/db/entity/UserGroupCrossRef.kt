package com.sunasterisk.appchat.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["user_id", "group_id"])
data class UserGroupCrossRef(
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "group_id")
    val groupId: String,
)


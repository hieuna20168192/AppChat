package com.sunasterisk.appchat.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(primaryKeys = ["user_id", "group_id"])
@Parcelize
data class UserGroupCrossRef(
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "group_id")
    val groupId: String,
) : Parcelable


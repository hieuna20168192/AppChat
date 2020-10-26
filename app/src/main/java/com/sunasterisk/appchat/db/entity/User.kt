package com.sunasterisk.appchat.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.sunasterisk.appchat.db.entity.User.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class User(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    var userId: String = "",
    @ColumnInfo(name = "user_name")
    var userName: String = "",
    var email: String? = "",
    @ColumnInfo(name = "profile_url")
    var profileUrl: String = "",
    @Ignore
    val groupIds: List<String> = emptyList(),
    @Ignore
    val chatIds: List<String> = emptyList(),
    @Ignore
    val friendIds: List<String> = emptyList(),
) : Parcelable {

    companion object {
        const val TABLE_NAME = "User"
    }
}

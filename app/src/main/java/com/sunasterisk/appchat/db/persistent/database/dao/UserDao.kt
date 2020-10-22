package com.sunasterisk.appchat.db.persistent.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.sunasterisk.appchat.db.entity.User
import com.sunasterisk.appchat.db.entity.UserWithChats
import com.sunasterisk.appchat.db.entity.UserWithGroups
import com.sunasterisk.appchat.db.persistent.database.DatabaseConstant.DEFAULT_CONFLICT_STRATEGY
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = DEFAULT_CONFLICT_STRATEGY)
    suspend fun insert(user: User)

    @Query("select * from User")
    fun getAllUsersFlow(): Flow<List<User>>

    @Transaction
    @Query("select * from User")
    fun getUserWithChats(): Flow<List<UserWithChats>>

    @Transaction
    @Query("select * from User")
    fun getUserWithGroups(): Flow<List<UserWithGroups>>
}

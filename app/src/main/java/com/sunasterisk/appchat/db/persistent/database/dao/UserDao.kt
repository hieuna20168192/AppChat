package com.sunasterisk.appchat.db.persistent.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sunasterisk.appchat.db.entity.User
import com.sunasterisk.appchat.db.persistent.database.DatabaseConstant.DEFAULT_CONFLICT_STRATEGY
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = DEFAULT_CONFLICT_STRATEGY)
    suspend fun insert(user: User)

    @Query("select * from user")
    fun getAllUsersFlow(): Flow<List<User>>
}

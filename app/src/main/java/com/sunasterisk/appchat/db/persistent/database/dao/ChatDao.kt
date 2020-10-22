package com.sunasterisk.appchat.db.persistent.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.sunasterisk.appchat.db.entity.*
import com.sunasterisk.appchat.db.persistent.database.DatabaseConstant
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Transaction
    @Query("select * from Chat")
    fun getChatsWithMessages(): Flow<List<ChatMessages>>

    @Transaction
    @Query("select * from `Group`")
    fun getGroupsWithMessages(): Flow<List<GroupMessages>>

    @Transaction
    @Query("select * from Chat")
    fun getChatsWithUsers(): Flow<List<ChatWithUsers>>

    @Transaction
    @Query("select * from `Group`")
    fun getGroupsWithUsers(): Flow<List<GroupWithUsers>>

    @Insert(onConflict = DatabaseConstant.DEFAULT_CONFLICT_STRATEGY)
    suspend fun insertChat(chat: Chat)

    @Insert(onConflict = DatabaseConstant.DEFAULT_CONFLICT_STRATEGY)
    suspend fun insertGroup(group: Group)
}

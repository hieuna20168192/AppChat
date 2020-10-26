package com.sunasterisk.appchat.db.firebase.service

import com.sunasterisk.appchat.db.Result
import com.sunasterisk.appchat.db.entity.Chat
import com.sunasterisk.appchat.db.entity.Group
import com.sunasterisk.appchat.db.entity.User
import kotlinx.coroutines.flow.Flow

interface ClientService {
    fun getChats(userId: String): Flow<Result<List<Chat>>>
    fun getGroups(userId: String): Flow<Result<List<Group>>>
    fun getFriends(userId: String): Flow<Result<List<User>>>
    fun getAllUsers(): Flow<Result<List<User>>>
}

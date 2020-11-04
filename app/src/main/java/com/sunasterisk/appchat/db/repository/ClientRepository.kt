package com.sunasterisk.appchat.db.repository

import com.sunasterisk.appchat.db.Result
import com.sunasterisk.appchat.db.entity.*
import com.sunasterisk.appchat.db.firebase.service.ClientService
import com.sunasterisk.appchat.db.persistent.database.dao.ChatDao
import com.sunasterisk.appchat.db.persistent.database.dao.UserDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class ClientRepository(
    private val clientService: ClientService,
    private val chatDao: ChatDao,
    private val userDao: UserDao,
) : Repository.ClientRepository {

    override fun getChats(userId: String): Flow<Result<List<Chat>>> =
        userDao.getUserWithChats()
            .flatMapLatest { userWithChats ->
                if (userWithChats.any { it.user.userId == userId && it.chats.isNotEmpty() }) {
                    flow {
                        val result =
                            Result.success(userWithChats.first { it.user.userId == userId }.chats)
                        emit(result)
                    }
                } else {
                    clientService.getChats(userId).onEach { chatListResult ->
                        if (chatListResult is Result.Success) {
                            chatListResult.data.asFlow().buffer()
                                .onEach { chat ->
                                    val userChatCrossRef =
                                        UserChatCrossRef(userId = userId, chatId = chat.chatId)
                                    chatDao.insertChat(chat)
                                    userDao.insert(userChatCrossRef)
                                }
                                .collect()
                        }
                    }
                }
            }

    override fun getGroups(userId: String): Flow<Result<List<Group>>> =
        userDao.getUserWithGroups(userId).flatMapLatest { userWithGroups ->
            if (userWithGroups.groups.isNotEmpty()) {
                flow {
                    emit(Result.success(userWithGroups.groups))
                }
            } else {
                clientService.getGroups(userId).onEach { groupListResult ->
                    if (groupListResult is Result.Success) {
                        groupListResult.data.asFlow().buffer()
                            .onEach { group ->
                                val userGroupCrossRef =
                                    UserGroupCrossRef(userId = userId, groupId = group.groupId)
                                chatDao.insertGroup(group)
                                userDao.insert(userGroupCrossRef)
                            }
                            .collect()
                    }
                }
            }
        }

    override fun getFriends(userId: String): Flow<Result<List<User>>> =
        clientService.getFriends(userId)

    override fun getAllUsers(): Flow<Result<List<User>>> =
        userDao.getAllUsersFlow().flatMapLatest { users ->
            if (users.isNotEmpty()) {
                flow {
                    emit(Result.success(users))
                }
            } else {
                clientService.getAllUsers().onEach { userListResult ->
                    if (userListResult is Result.Success) {
                        userListResult.data.asFlow()
                            .buffer().onEach { user ->
                                userDao.insert(user)
                            }
                            .collect()
                    }
                }
            }
        }
}

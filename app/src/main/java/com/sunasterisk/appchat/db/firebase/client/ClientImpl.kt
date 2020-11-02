package com.sunasterisk.appchat.db.firebase.client

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.sunasterisk.appchat.App
import com.sunasterisk.appchat.db.Result
import com.sunasterisk.appchat.db.entity.Chat
import com.sunasterisk.appchat.db.entity.Group
import com.sunasterisk.appchat.db.entity.Message
import com.sunasterisk.appchat.db.entity.User
import com.sunasterisk.appchat.db.firebase.RemoteConstant
import com.sunasterisk.appchat.db.firebase.RemoteConstant.getLastMessageId
import com.sunasterisk.appchat.db.firebase.service.ClientService
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class ClientImpl(
    fireStore: FirebaseFirestore,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ClientService {

    private val appResource = App.getContext().resources
    private val usersRef: CollectionReference = fireStore.collection(RemoteConstant.COLLECTION_USER)
    private val chatsRef: CollectionReference = fireStore.collection(RemoteConstant.COLLECTION_CHAT)
    private val groupsRef: CollectionReference =
        fireStore.collection(RemoteConstant.COLLECTION_GROUP)

    override fun getChats(userId: String): Flow<Result<List<Chat>>> =
        callbackFlow {
            val chats = mutableListOf<Chat>()
            val listenerAuthUser = usersRef.document(userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val chatIds: List<String> =
                        snapshot?.toObject<User>()?.chatIds ?: emptyList()
                    chats.clear()
                    val chatFlow = chatIds.asFlow().buffer().onEach { chatId ->
                        val recentChatMessageRef = chatsRef.document(chatId)
                            .collection(RemoteConstant.COLLECTION_RECENT_MESSAGE_CHAT)

                        val lastMessageId = getLastMessageId(chatId)
                        val chatDeferred = chatsRef.document(chatId).get().await().toObject<Chat>()

                        val lastMessageDeferred = recentChatMessageRef.document(lastMessageId).get()
                            .await().toObject<Message>()

                        chatDeferred?.run {
                            recentMessage = lastMessageDeferred
                            chats.add(this)
                        }
                    }.onCompletion {
                        offer(Result.success(chats.toList()))
                    }
                    launch(defaultDispatcher) {
                        chatFlow.collect()
                    }
                }
            awaitClose {
                listenerAuthUser.remove()
            }
        }

    override fun getGroups(userId: String): Flow<Result<List<Group>>> =
        callbackFlow {
            val groups = mutableListOf<Group>()
            val listenerAuthUser = usersRef.document(userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val groupIds: List<String> =
                        snapshot?.toObject<User>()?.groupIds ?: emptyList()
                    groups.clear()
                    val groupFlow = groupIds.asFlow().buffer().onEach { groupId ->

                        val groupDeferred =
                            groupsRef.document(groupId).get().await().toObject<Group>()
                        groupDeferred?.run {
                            groups.add(this)
                        }
                    }.onCompletion {
                        offer(Result.success(groups.toList()))
                    }
                    launch(defaultDispatcher) {
                        groupFlow.collect()
                    }
                }
            awaitClose {
                listenerAuthUser.remove()
            }
        }

    override fun getFriends(userId: String): Flow<Result<List<User>>> =
        callbackFlow {
            val friends = mutableListOf<User>()
            val listenerAuthUser = usersRef.document(userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val friendIds: List<String> =
                        snapshot?.toObject<User>()?.friendIds ?: emptyList()
                    friends.clear()
                    val friendFlow = friendIds.asFlow().buffer().onEach { friendId ->
                        val friendDeferred =
                            usersRef.document(friendId).get().await().toObject<User>()
                        friendDeferred?.run {
                            friends.add(this)
                        }
                    }.onCompletion {
                        offer(Result.success(friends.toList()))
                    }
                    launch(defaultDispatcher) {
                        friendFlow.collect()
                    }
                }
            awaitClose {
                listenerAuthUser.remove()
            }
        }

    override fun getAllUsers(): Flow<Result<List<User>>> =
        callbackFlow {
            var users: List<User>
            val listenerUsers = usersRef.addSnapshotListener { value, e ->
                if (e != null) {
                    close(e)
                    return@addSnapshotListener
                }
                users = value?.toObjects() ?: emptyList()
                offer(Result.success(users))
            }
            awaitClose {
                listenerUsers.remove()
            }
        }
}

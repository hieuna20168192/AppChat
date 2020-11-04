package com.sunasterisk.appchat.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.sunasterisk.appchat.base.BaseViewModel
import com.sunasterisk.appchat.db.Result
import com.sunasterisk.appchat.db.entity.Chat
import com.sunasterisk.appchat.db.persistent.pref.UserPref
import com.sunasterisk.appchat.db.repository.Repository
import com.sunasterisk.appchat.utils.query
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class ChatViewModel(
    clientRepository: Repository.ClientRepository
) : BaseViewModel() {

    private var _isEmptyLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isEmptyLiveData: LiveData<Boolean> = _isEmptyLiveData

    private val searchQueryChannel = ConflatedBroadcastChannel<String?>()

    @FlowPreview
    val chatsLiveData: LiveData<List<Chat>> =
        clientRepository.getChats(UserPref.getUserId())
            .onStart {
                _isEmptyLiveData.value = true
                _isError.value = ""
                searchQueryChannel.offer("")
            }
            .combine(searchQueryChannel.asFlow()) { chatsResult, query ->
                if (chatsResult is Result.Success) {
                    chatsResult.data.query(query)
                } else {
                    listOf()
                }
            }
            .onEach { chats ->
                if (chats.isEmpty()) {
                    _isEmptyLiveData.postValue(true)
                } else {
                    _isEmptyLiveData.postValue(false)
                }
            }
            .catch { cause: Throwable ->
                _isError.postValue(cause.message)
            }
            .conflate()
            .asLiveData()

    fun offerSearchQuery(keyword: String?) {
        searchQueryChannel.offer(keyword)
    }
}

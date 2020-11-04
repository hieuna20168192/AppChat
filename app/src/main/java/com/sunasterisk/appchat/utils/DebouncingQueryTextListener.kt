package com.sunasterisk.appchat.utils

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import com.sunasterisk.appchat.db.firebase.RemoteConstant.DEBOUNCE_PERIOD
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class DebounceQueryTextListener(
    lifecycle: Lifecycle,
    private val onDebounceQueryTextChange: (String?) -> Unit,
) : SearchView.OnQueryTextListener {
    private var debouncePeriod: Long = DEBOUNCE_PERIOD
    private val coroutineScope = lifecycle.coroutineScope
    private var searchJob: Job? = null

    override fun onQueryTextSubmit(query: String?) = false

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newText?.let {
                delay(debouncePeriod)
                onDebounceQueryTextChange(newText)
            }
        }
        return false
    }
}

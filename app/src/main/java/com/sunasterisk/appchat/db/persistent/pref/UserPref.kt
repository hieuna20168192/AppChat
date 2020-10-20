package com.sunasterisk.appchat.db.persistent.pref

import android.content.Context
import com.sunasterisk.appchat.App

object UserPref {

    private const val CACHED_USER = "cached_user"
    private const val USER_ID_KEY = "userId"
    private val pref = App.getContext().getSharedPreferences(CACHED_USER, Context.MODE_PRIVATE)

    fun saveUserId(userId: String) = pref.edit().putString(USER_ID_KEY, userId).apply()
    fun getUserId() = pref.getString(USER_ID_KEY, "") ?: ""
}

package com.sunasterisk.appchat.db.repository

import com.sunasterisk.appchat.db.firebase.service.AuthService
import com.sunasterisk.appchat.db.persistent.database.dao.UserDao

class UserRepository(
    private val authService: AuthService,
    private val userDao: UserDao
) : Repository {

    override var isLoading = false

    suspend fun logIn(username: String, password: String) =
        authService.logIn(username, password)
}

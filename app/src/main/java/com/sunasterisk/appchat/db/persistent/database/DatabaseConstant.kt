package com.sunasterisk.appchat.db.persistent.database

import androidx.room.OnConflictStrategy

object DatabaseConstant {

    const val DATABASE_NAME = "app_chat.db"
    const val DATABASE_VERSION = 1
    const val DEFAULT_CONFLICT_STRATEGY = OnConflictStrategy.REPLACE
}

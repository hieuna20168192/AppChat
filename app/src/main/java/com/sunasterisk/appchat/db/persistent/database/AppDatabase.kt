package com.sunasterisk.appchat.db.persistent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sunasterisk.appchat.db.entity.User
import com.sunasterisk.appchat.db.persistent.database.dao.UserDao

@Database(
    entities = [
        User::class,
    ],
    version = DatabaseConstant.DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}

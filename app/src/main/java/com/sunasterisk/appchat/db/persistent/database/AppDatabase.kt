package com.sunasterisk.appchat.db.persistent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sunasterisk.appchat.db.entity.*
import com.sunasterisk.appchat.db.persistent.converters.DateTimeConverter
import com.sunasterisk.appchat.db.persistent.database.dao.ChatDao
import com.sunasterisk.appchat.db.persistent.database.dao.UserDao

@Database(
    entities = [
        User::class,
        Chat::class,
        Group::class,
        Message::class,
        UserChatCrossRef::class,
        UserGroupCrossRef::class,
    ],
    version = DatabaseConstant.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun chatDao(): ChatDao
}

package com.sunasterisk.appchat.di

import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sunasterisk.appchat.db.firebase.client.AuthImpl
import com.sunasterisk.appchat.db.firebase.service.AuthService
import com.sunasterisk.appchat.db.persistent.database.AppDatabase
import com.sunasterisk.appchat.db.persistent.database.DatabaseConstant
import com.sunasterisk.appchat.db.repository.Repository
import com.sunasterisk.appchat.db.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val repositoryModule = module {

    // Firebase modules
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }

    // Room database modules
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            DatabaseConstant.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    // Room Dao modules
    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().chatDao() }

    // Firebase modules
    single<AuthService> { AuthImpl(get(), get(), get()) }

    // DataSource modules
    single<Repository> { UserRepository(get(), get(), get()) }
}

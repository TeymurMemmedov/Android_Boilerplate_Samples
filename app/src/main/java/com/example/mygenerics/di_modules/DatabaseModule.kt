package com.example.mygenerics.di_modules

import android.content.Context
import androidx.room.Room
import com.example.mygenerics.idle_classes.RandomDatabase
import com.example.mygenerics.idle_classes.SomeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private typealias MyRoomDatabase = RandomDatabase
private typealias SomeDao = SomeDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    const val db_name = ""

    @Provides
    @Singleton
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ): MyRoomDatabase {
        return Room.databaseBuilder(
            context,
            MyRoomDatabase::class.java,
            db_name).build()
    }


    @Provides
    @Singleton
    fun provideDao(database:MyRoomDatabase): SomeDao {
        return database.someDao()
    }
}
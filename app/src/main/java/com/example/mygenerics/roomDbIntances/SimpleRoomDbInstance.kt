package com.example.mygenerics.roomDbIntances

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//Dont forget change dao,entity,database names
@Database(entities = [], version = 1)
abstract class SimpleRoomDbInstance : RoomDatabase() {
    abstract fun anyDao(): Any

    companion object {
        @Volatile
        private var INSTANCE: SimpleRoomDbInstance? = null
        fun getInstance(context: Context, ): SimpleRoomDbInstance {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SimpleRoomDbInstance::class.java,
                    "any.db"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }
}
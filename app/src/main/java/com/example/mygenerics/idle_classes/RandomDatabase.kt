package com.example.mygenerics.idle_classes

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [], version = 1)
abstract class RandomDatabase:RoomDatabase() {
    abstract fun someDao():SomeDao

}
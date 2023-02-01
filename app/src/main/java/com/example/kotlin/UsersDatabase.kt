package com.example.kotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Users::class], version = 1)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun UserDao() : UserDao

    companion object {

        @Volatile
        private var INSTANCE : UsersDatabase? = null

        fun getDatabase(context: Context) : UsersDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersDatabase::class.java,
                    "UsersDB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
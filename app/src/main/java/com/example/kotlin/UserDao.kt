package com.example.kotlin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(UserDao: Users)

    @Query("SELECT Mobile, Password FROM Users WHERE Mobile LIKE :mobNo")
    suspend fun getInfo(mobNo : Int) :Users
}
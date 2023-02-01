package com.example.kotlin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Users", indices = [(Index(value = ["Mobile"], unique = true))])
data class Users(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    @ColumnInfo(name = "FirstName") val firstName : String?,
    @ColumnInfo(name = "LastName") val lastName : String?,
    @ColumnInfo(name = "Email") val email : String?,
    @ColumnInfo(name = "Mobile") val mob_no : Int?,
    @ColumnInfo(name = "Password") val pass : String?,
    @ColumnInfo(name = "City") val city : String?
)

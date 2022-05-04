package com.utn.cookapp.entities

import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "usersTab")
class User(
    @PrimaryKey
    @ColumnInfo(name = "Id")
    var id : Int,
    @ColumnInfo(name = "Username")
    var user : String,
    @ColumnInfo(name = "Pass")
    var pass : String,
    @ColumnInfo(name = "Age")
    var age : String,
    @ColumnInfo(name = "ProfileImage")
    var profileImage : String
    ) : Parcelable
{
}
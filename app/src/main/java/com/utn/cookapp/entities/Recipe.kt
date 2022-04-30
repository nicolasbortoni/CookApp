package com.utn.cookapp.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Parcelize
@Entity(tableName = "recipesTab")
class Recipe(
    @PrimaryKey
    @ColumnInfo(name = "Id")
    var id : Int,
    @ColumnInfo(name = "Name")
    var name : String,
    @ColumnInfo(name = "Author")
    var author : String,
    @ColumnInfo(name = "Image")
    var image : String,
    @ColumnInfo(name = "Recipe")
    var recipe : String
) : Parcelable
{
}
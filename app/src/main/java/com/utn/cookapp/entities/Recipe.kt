package com.utn.cookapp.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Recipe(var name : String, var author : String, var image : String, var recipe : String) : Parcelable
{
}
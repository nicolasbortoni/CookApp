package com.utn.cookapp.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(var user : String,var pass : String, var age : Int, var profileImage : String) : Parcelable
{
}
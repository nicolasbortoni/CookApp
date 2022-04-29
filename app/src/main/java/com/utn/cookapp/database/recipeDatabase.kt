package com.utn.cookapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.utn.cookapp.entities.Recipe

@Database(entities = [Recipe::class], version = 1, exportSchema = false)

public  abstract class recipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): recipeDao

    companion object {
        var INSTANCE: recipeDatabase? = null

        fun getAppDataBase(context: Context): recipeDatabase? {
            if (INSTANCE == null) {
                synchronized(recipeDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        recipeDatabase::class.java,
                        "recipesDB"
                    ).allowMainThreadQueries().build() // No es lo mas recomendable que se ejecute en el mainthread
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}
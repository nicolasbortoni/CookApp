package com.utn.cookapp.database

import androidx.room.*
import com.utn.cookapp.entities.Recipe

@Dao
public interface recipeDao {

    @Query("SELECT * FROM recipesTab ORDER BY id")
    fun loadAllPersons(): MutableList<Recipe?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(recipe: Recipe?)

    @Update
    fun updatePerson(recipe: Recipe?)

    @Delete
    fun delete(recipe: Recipe?)

    @Query("SELECT * FROM recipesTab WHERE id = :id")
    fun loadPersonById(id: Int): Recipe?

    @Query("SELECT COUNT(*) FROM recipesTab")
    fun getCount(): Int
}
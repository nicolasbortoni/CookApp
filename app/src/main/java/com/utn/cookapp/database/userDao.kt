package com.utn.cookapp.database

import androidx.room.*
import com.utn.cookapp.entities.User

@Dao
public interface userDao {

    @Query("SELECT * FROM usersTab ORDER BY id")
    fun loadAllPersons(): MutableList<User?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(user: User?)

    @Delete
    fun delete(user: User?)


}
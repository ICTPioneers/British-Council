package com.example.british_council.database.dao

import androidx.room.*
import com.example.british_council.model.Level
import com.example.british_council.model.State

@Dao
interface StateDao {
    @Query("select * from level where id=:id")
    fun getLeve(id: Int): Level

    @Query("UPDATE level SET active=:active WHERE id=:id")
    fun updateState(active: Int, id: Int)



    @Insert
    fun insert(state: State)
    @Delete
    fun delete(state: State)
    @Update
    fun update(state: State)


}
package com.example.british_council.database.dao

import androidx.room.*
import com.example.british_council.model.Level

@Dao
interface LevelDao {
    @Query("select * from level where id=:id")
    fun getLeve(id: Int): Level

    @Query("UPDATE level SET active=:active WHERE id=:id")
    fun updateState(active: Int, id: Int)



    @Insert
    fun insert(level: ArrayList<Level>)
    @Delete
    fun delete(level: Level)
    @Update
    fun update(level: Level)


}
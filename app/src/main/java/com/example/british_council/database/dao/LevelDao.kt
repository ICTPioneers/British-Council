package com.example.british_council.database.dao

import androidx.room.*
import com.example.british_council.model.Level

@Dao
interface LevelDao {
    @Query("select * from level")
    fun getAllLeve(): List<Level>

    @Query("select * from level where id=:id")
    fun getLeve(id: Int): Level

    @Query("UPDATE level SET states=:states WHERE id=:id")
    fun updateState(states: Int, id: Int)

    @Insert
    fun insertState(level: Level) : Long?


    @Insert
    fun insert(level: ArrayList<Level>)
    @Delete
    fun delete(level: Level)
    @Update
    fun update(level: Level)


}
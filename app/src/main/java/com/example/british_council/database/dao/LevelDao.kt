package com.example.british_council.database.dao

import androidx.room.*
import com.example.british_council.model.Level

@Dao
interface LevelDao {
    @Insert
    fun insert(level: ArrayList<Level>)
    @Delete
    fun delete(level: Level)
    @Update
    fun update(level: Level)


    @Insert
    fun insertID(id: Level)
    //    fun insertID(id: ArrayList<Int>)
    @Query("select * from level where id=:id")
    fun getLeve(id: Int): Level

    @Query("select active from level")
    fun getIdLevel(): List<Int>


    @Query("select * from level")
    fun getAllLeve(): List<Level>

//    @Query("UPDATE level SET password = :pass")
//    fun updateUserPass(pass: String?)


    @Query("UPDATE level SET active = :active WHERE id = :id")
    fun updateState(active: Int, id: Int)


}
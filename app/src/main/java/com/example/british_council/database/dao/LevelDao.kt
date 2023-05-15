package com.example.british_council.database.dao

import androidx.room.*
import com.example.british_council.model.Level

@Dao
interface LevelDao {
    @Insert
    fun insert(level: ArrayList<Level>)
//    @Delete
//    fun delete(level: Level)
//    @Update
//    fun update(level: Level)

    @Query("select * from level where id=:id")
    fun getLeve(id : Int): Level

//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertNote(levelModel: LevelModel)
//
//    @Delete
//    suspend fun deleteNote(levelModel: LevelModel)
//
//    @Update
//    suspend fun update(levelModel: LevelModel)
//
//    @Query("SELECT * FROM level_table ORDER BY id ASC")
////    fun getAllNotes(): LiveData<List<LevelModel>>
//    fun getAllNotes(): List<LevelModel>
//
//
//    */

}
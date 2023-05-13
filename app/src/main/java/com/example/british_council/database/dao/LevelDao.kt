package com.example.british_council.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.british_council.model.LevelModel

@Dao
interface LevelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(levelModel: LevelModel)

    @Delete
    suspend fun deleteNote(levelModel: LevelModel)

    @Update
    suspend fun update(levelModel: LevelModel)

    @Query("SELECT * FROM level_table ORDER BY id ASC")
//    fun getAllNotes(): LiveData<List<LevelModel>>
    fun getAllNotes(): List<LevelModel>
}
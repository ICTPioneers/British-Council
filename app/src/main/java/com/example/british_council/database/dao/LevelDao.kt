package com.example.british_council.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.british_council.model.LevelModel

@Dao
interface LevelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: LevelModel)

    @Delete
    suspend fun deleteNote(noteEntity: LevelModel)

    @Update
    suspend fun update(noteEntity: LevelModel)

    @Query("SELECT * FROM level_table ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<LevelModel>>
}
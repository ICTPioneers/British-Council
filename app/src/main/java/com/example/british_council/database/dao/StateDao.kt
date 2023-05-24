package com.example.british_council.database.dao

import androidx.room.*
import com.example.british_council.model.Level
import com.example.british_council.model.State

@Dao
interface StateDao {
    @Query("select * from state")
    fun getAllState():List<State>

@Query("select state from state where state=:id")
    fun getId(id : Int):List<State>

    @Insert
    fun insert(state: State)
    @Delete
    fun delete(state: State)
    @Update
    fun update(state: State)


}
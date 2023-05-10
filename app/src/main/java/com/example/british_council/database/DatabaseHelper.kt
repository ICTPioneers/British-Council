package com.example.british_council.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.british_council.database.dao.LevelDao
import com.example.british_council.model.LevelModel

@Database(entities = [LevelModel::class], version = 1)
abstract class DatabaseHelper : RoomDatabase() {
    abstract val dao: LevelDao

    companion object{
        @Volatile
        private var instance: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper?{
            if (instance == null){
                synchronized(DatabaseHelper::class.java){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseHelper::class.java,
                        "notes"
                    )
                        .addCallback(StartingLevel(context))
                        .build()
                }
            }
            return instance
        }
    }
}
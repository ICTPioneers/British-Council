package com.example.british_council.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.british_council.database.dao.LevelDao
import com.example.british_council.model.LevelModel


@Database(entities = [LevelModel::class], version = 1)
abstract class DatabaseHelper : RoomDatabase() {
    abstract val dao: LevelDao

    companion object {
        private const val DATABASE_NAME = "level_table"
        @Volatile
        private var instance: DatabaseHelper? = null


        open fun getInstance(context: Context): DatabaseHelper? {
            if (instance == null) {
                instance = databaseBuilder(
                    context.applicationContext,
                    DatabaseHelper::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }


    }

    //
//    companion object{
//        @Volatile
//        private var instance: DatabaseHelper? = null
//
//        fun getInstance(context: Context): DatabaseHelper?{
//            if (instance == null){
//                synchronized(DatabaseHelper::class.java){
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        DatabaseHelper::class.java,
//                        "notes"
//                    )
//                        .addCallback(StartingLevel(context))
//                        .build()
//                }
//            }
//            return instance
//        }
//    }

}
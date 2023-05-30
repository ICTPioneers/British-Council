package ir.ictpioneers.british_council.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.ictpioneers.british_council.database.dao.LevelDao
import ir.ictpioneers.british_council.model.TextConverter
import ir.ictpioneers.british_council.model.Level
import ir.ictpioneers.british_council.model.State

@Database(entities = [Level::class , State::class], version = 1)

@TypeConverters(TextConverter::class)
abstract class DatabaseHelper : RoomDatabase() {
    abstract val levelDao: LevelDao

    companion object {
        private const val DATABASE_NAME = "level_table.db"
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

}
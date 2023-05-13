package com.example.british_council.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.british_council.R
import com.example.british_council.model.LevelModel
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.BufferedReader

class StartingLevel(private val context: Context) :RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
//        CoroutineScope(Dispatchers.IO).launch {
//            fillWithStartingNotes(context)
        }
    }

    private suspend fun fillWithStartingNotes(context: Context){
        val dao = DatabaseHelper.getInstance(context)?.dao

        try {
            val notes1 = loadObject(context)
           val notes = notes1!!.getJSONArray("level")
            if (notes != null){
                for (i in 0 until notes.length()){
                    val item = notes.getJSONObject(i)
                    val noteTitle = item.getString("name")
                    val notesDescription = item.getString("desc")

                     val noteEntity = LevelModel(
                        noteTitle,notesDescription
                    )

                    dao?.insertNote(noteEntity)
                }
            }
        }
        catch (e:JSONException) {
            Timber.d("fillWithStartingNotes: $e")
        }
    }

    private fun loadObject(context: Context):JSONObject?{
        val inputStream = context.resources.openRawResource(R.raw.example_leaen_en)
        BufferedReader(inputStream.reader()).use {
            return JSONObject(it.readText())
        }
    }
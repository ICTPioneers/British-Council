package ir.ictpioneers.british_council.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TextConverter {
    @TypeConverter
    fun fromCountryLangList(value: List<Text>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Text>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCountryLangList(value: String): List<Text> {
        val gson = Gson()
        val type = object : TypeToken<List<Text>>() {}.type
        return gson.fromJson(value, type)
    }
}
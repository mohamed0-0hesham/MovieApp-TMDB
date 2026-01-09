package com.hesham0_0.movietrainingapplication.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun listToString(value:String?):List<String>{
        val listType =object : TypeToken<List<String>>(){}.type
        return Gson().fromJson(value,listType)
    }

    @TypeConverter
    fun stringToList(list: List<String>): String {
        return Gson().toJson(list)
    }
}
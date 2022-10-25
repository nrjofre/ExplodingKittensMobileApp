package com.example.explodingkittensapp.database

import androidx.room.TypeConverter

private const val SEPARATOR = ","

class Converters {
    @TypeConverter
    fun fromDeckToString(deck: MutableList<String>?): String?{
        return deck?.map { it }?.joinToString(separator = SEPARATOR)
    }

    @TypeConverter
    fun fromStringToDeck(deck: String?): MutableList<String>?{
        return deck?.split(SEPARATOR)?.map { it }?.toMutableList()
    }
}
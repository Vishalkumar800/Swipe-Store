package com.rach.swipestore.data.local

import android.net.Uri
import androidx.room.TypeConverter
import androidx.core.net.toUri

class Converters {

    @TypeConverter
    fun fromUriList(value: List<Uri>?): String? {
        return value?.joinToString(",") { it.toString() }
    }

    @TypeConverter
    fun toUriList(value: String?): List<Uri>? {
        return value?.split(",")?.map { it.toUri() }
    }
}

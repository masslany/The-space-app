package com.masslany.thespaceapp.data.local.cache

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.masslany.thespaceapp.data.remote.response.launches.Core

class Converters {

    @TypeConverter
    fun coreListToJson(value: List<Core>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToCoreList(value: String) = Gson().fromJson(value, Array<Core>::class.java).toList()

    @TypeConverter
    fun stringListToJson(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToStringList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun uriToString(uri: Uri?): String? = uri?.toString()

    @TypeConverter
    fun stringToUri(string: String?): Uri? = if (string == null) {
        null
    } else {
        Uri.parse(string)
    }
}
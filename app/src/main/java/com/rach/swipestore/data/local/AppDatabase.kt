package com.rach.swipestore.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import retrofit2.Retrofit

@Database(
    entities = [Entity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dao(): Dao

    companion object{

        @Volatile
        private var instances : AppDatabase? =null

        fun invoke(context: Context): AppDatabase{
            return instances ?: synchronized(this){
                instances ?: createDatabase(context)
            }
        }

        private fun createDatabase(
            context: Context
        ): AppDatabase{
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "room.db"
            ).build()
        }
    }
}
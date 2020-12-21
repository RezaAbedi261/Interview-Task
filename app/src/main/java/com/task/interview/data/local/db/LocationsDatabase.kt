package com.task.interview.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.interview.data.local.db.dao.LocationsDao
import com.task.interview.model.CategoryConverter
import com.task.interview.model.PlaceInfo
import com.task.interview.utils.DB_NAME


@Database(
    entities = arrayOf(PlaceInfo::class),
    version = 1
)
@TypeConverters(CategoryConverter::class)
abstract class LocationsDatabase : RoomDatabase() {

    abstract fun getLocationsDbDao(): LocationsDao

    companion object {

        @Volatile
        private var INSTANCE: LocationsDatabase? = null

        fun getInstance(context: Context): LocationsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocationsDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}

package com.task.interview.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.interview.data.local.db.dao.TestDao
import com.task.interview.model.TestDbTable
import com.task.interview.utils.DB_NAME


@Database(
    entities = arrayOf(TestDbTable::class),
    version = 1
)
@TypeConverters
abstract class TestDatabase : RoomDatabase() {

    abstract fun getTestDbDao(): TestDao

    companion object {

        @Volatile
        private var INSTANCE: TestDatabase? = null

        fun getInstance(context: Context): TestDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TestDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}

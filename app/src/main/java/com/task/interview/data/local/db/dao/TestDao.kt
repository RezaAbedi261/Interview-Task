package com.task.interview.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.interview.model.TestDbTable


@Dao
interface TestDao {

    @Query("SELECT * FROM TestDbTable LIMIT :limit OFFSET :offset ")
    suspend fun getAll(limit: Int, offset: Int): List<TestDbTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(venues: List<TestDbTable>)

    @Query("DELETE FROM TestDbTable")
    suspend fun deleteAll()

}

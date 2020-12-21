package com.task.interview.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.interview.model.PlaceInfo


@Dao
interface LocationsDao {

    @Query("SELECT * FROM PlaceInfo")
    suspend fun getAll(): List<PlaceInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(venues: List<PlaceInfo>)

    @Query("DELETE FROM PlaceInfo")
    suspend fun deleteAll()

}

package com.task.interview.model

import androidx.room.*
import java.io.Serializable

@Entity
data class TestDbTable(
    @PrimaryKey val id: String
 ) : Serializable
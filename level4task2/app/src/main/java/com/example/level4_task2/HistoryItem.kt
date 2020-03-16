package com.example.level4_task2

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.sql.Timestamp
import java.util.*

@Entity
data class HistoryItem(
        @PrimaryKey(autoGenerate = true) var id : Int?,
        @ColumnInfo(name = "result") var result: String,
        @ColumnInfo(name = "cpuMove") var cpuMove: Int,
        @ColumnInfo(name = "playerMove") var playerMove: Int
)

package com.example.level4_task2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryItem(
        @PrimaryKey(autoGenerate = true) var id : Int?,
        @ColumnInfo(name = "result") var result: String,
        @ColumnInfo(name = "date") var date: String,
        @ColumnInfo(name = "cpuMove") var cpuMove: Int,
        @ColumnInfo(name = "playerMove") var playerMove: Int
)



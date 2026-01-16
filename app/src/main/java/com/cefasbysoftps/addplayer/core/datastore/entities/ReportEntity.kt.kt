package com.cefasbysoftps.addplayer.core.datastore

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime


@Entity(tableName = "report")
data class ReportEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val secondsPlayed: Long,
    val date: Long,
    val startPlay: Long,
    val endPlay: Long
)
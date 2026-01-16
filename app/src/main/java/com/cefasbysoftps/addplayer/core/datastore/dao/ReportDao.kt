package com.cefasbysoftps.addplayer.core.datastore

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReportDao {
    @Query("SELECT * FROM report")
    suspend fun getAll(): List<ReportEntity>

    @Insert
    suspend fun insert(reportEntity: ReportEntity)

    @Query("SELECT * FROM report WHERE userId = :userId")
    suspend fun getByUser(userId: Int): List<ReportEntity>

    @Query("SELECT * FROM report WHERE userId = :userId and date=:date")
    suspend fun getByUserToday(userId: Int, date: Long): List<ReportEntity>

    @Query("SELECT date," +
            " SUM(secondsPlayed) AS secondsPlayed," +
            "0 as userId,0 as startPlay,0 as endPlay,0 as id" +
            " FROM report WHERE userId = :userId " +
            "GROUP BY date" +
            "    ORDER BY date DESC")
    suspend fun getUserSummary(userId: Int): List<ReportEntity>
}
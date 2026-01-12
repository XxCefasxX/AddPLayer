package com.cefasbysoftps.addplayer.core.datastore

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReportDao{
    @Query("SELECT * FROM report")
    suspend fun  getAll(): List<ReportEntity>

    @Insert
    suspend fun insert(reportEntity: ReportEntity)

    @Query("SELECT * FROM report WHERE userId = :userId")
    suspend fun  getByUser(userId:Int): List<ReportEntity>
}
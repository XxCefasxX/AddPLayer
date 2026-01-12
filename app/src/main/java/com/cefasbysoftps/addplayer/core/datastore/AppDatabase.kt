package com.cefasbysoftps.addplayer.core.datastore
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ReportEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun reportDao(): ReportDao
    companion object {
        const val DATABASE_NAME = "adplayer_db"
    }
}
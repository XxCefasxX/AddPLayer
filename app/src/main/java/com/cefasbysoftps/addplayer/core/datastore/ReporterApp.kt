


import android.app.Application
import android.content.Context
import androidx.room.Room


class ReporterApp : Application(){

//    lateinit var database: AppDatabase
//        private set
//
//    override fun onCreate() {
//        super.onCreate()
//        database = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java,
//            "app_db"
//        ).build()
//    }
//
//    companion object {
//        private lateinit var instance: ReporterApp
//        fun getInstance(): ReporterApp = instance
//    }

//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(application: Application): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    application,
//                    AppDatabase::class.java,
//                    AppDatabase.DATABASE_NAME
//                )
//                    .fallbackToDestructiveMigration()  // ¡IMPORTANTE para desarrollo!
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }

//    val room= Room.databaseBuilder(this,AppDatabase::class.java,"adplayer_db").build()

}
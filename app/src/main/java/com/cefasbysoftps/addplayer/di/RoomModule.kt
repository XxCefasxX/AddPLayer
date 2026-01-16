//import android.content.Context
//import androidx.room.Room
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object RoomModule {
//
//    const val REPORT_DATABASE_NAME = "REPORT_DATABASE"
//
//    @Singleton
//    @Provides
//    fun provideRoom(@ApplicationContext context: Context) =
//        Room.databaseBuilder(context, AppDatabase::class.java, REPORT_DATABASE_NAME).build()
//
//    @Singleton
//    @Provides
//    fun provideReportDao(db:AppDatabase)=db.reportDao()
//}
import com.cefasbysoftps.addplayer.core.datastore.ReportDao
import com.cefasbysoftps.addplayer.core.datastore.ReportEntity

    class ReportRepositoryImpl(
        private val reportDao: ReportDao,
        private val api: ReportApi
) : IReportRepository {


    override suspend fun sendReport(
        userId: Int,
        fecha: String,
        tiempoMinutos: Int
    ): Result<ReportResponse> {
        return try {
            val response = api.sendReport(
                ReportRequest(userId, fecha, tiempoMinutos)
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loadUserLocalReports(userId: Int): List<ReportEntity> {
       return reportDao.getByUser(userId)
    }
}
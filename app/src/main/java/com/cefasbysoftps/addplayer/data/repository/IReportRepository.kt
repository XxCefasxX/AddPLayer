import com.cefasbysoftps.addplayer.core.datastore.ReportEntity

interface IReportRepository {
    suspend fun sendReport(
        userId: Int,
        fecha: String,
        tiempoMinutos: Int
    ): Result<ReportResponse>

    suspend fun loadUserLocalReports(userId: Int): List<ReportEntity>

    suspend fun loadUseTodayLocalReports(userId: Int, date: Long): List<ReportEntity>

    suspend fun loadUserSummary(userId: Int): List<ReportEntity>
}
interface IReportRepository {
    suspend fun sendReport(   userId: Int,
                              fecha: String,
                              tiempoMinutos: Int): Result<ReportResponse>
}
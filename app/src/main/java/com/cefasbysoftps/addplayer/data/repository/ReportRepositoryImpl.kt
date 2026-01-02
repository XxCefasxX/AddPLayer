class ReportRepositoryImpl(
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
}
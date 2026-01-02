class ReportUseCase (
    private val repository: IReportRepository
){
    suspend operator fun invoke(
        userId: Int,
        fecha: String,
        tiempoMinutos: Int
    ): Result<ReportResponse>{
        return  repository.sendReport(userId,fecha,tiempoMinutos)
    }
}
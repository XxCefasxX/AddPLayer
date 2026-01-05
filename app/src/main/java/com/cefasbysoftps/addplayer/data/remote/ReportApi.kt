import retrofit2.http.Body
import retrofit2.http.POST

interface ReportApi{

    @POST("Report/crear-reporte")
        suspend fun sendReport(
            @Body request: ReportRequest
        ):ReportResponse
}
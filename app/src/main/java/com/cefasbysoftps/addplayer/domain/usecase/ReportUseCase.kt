import androidx.core.content.edit
import com.cefasbysoftps.addplayer.core.datastore.ReportEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Collections.list

class ReportUseCase(
    private val repository: IReportRepository
) {


    suspend operator fun invoke(
        userId: Int,
        fecha: String,
        tiempoMinutos: Int
    ): Result<ReportResponse> {
        return repository.sendReport(userId, fecha, tiempoMinutos)
    }

    suspend fun loadUserLocalReports(userId: Int): List<ReportEntity> {
        return repository.loadUserLocalReports((userId))
    }

    suspend fun todayTime(userId: Int, date: Long): Long {
        val registros = repository.loadUseTodayLocalReports(userId, date)
        var litsa: List<ReportEntity>
        var total: Long = 0
        for (reporte in registros) {
            total = +reporte.secondsPlayed
        }
        return total
    }

    suspend fun LoadLocalToday(userId: Int, date: Long): List<ReportEntity> {
        val registros = repository.loadUseTodayLocalReports(userId, date)
        var litsa: List<ReportEntity>
        var total: Long = 0
        for (reporte in registros) {
            total = +reporte.secondsPlayed
        }

        return repository.loadUseTodayLocalReports(userId, date)
    }


    suspend fun loadUserSummary(userId: Int): List<ReportEntity> {
//        val lista = repository.loadUserSummary(userId)
//        var reportes = mutableListOf<ReportEntity>()
//        for (report in lista) {
//            var reporte =
//                ReportEntity(
//                    userId = userId.toString(),
//                    secondsPlayed = report.secondsPlayed,
//                    date = report.date,
//                    startPlay = 0,
//                    endPlay = 0
//                )
//            reportes.add(reporte)
//        }
        return repository.loadUserSummary(userId)
    }
}
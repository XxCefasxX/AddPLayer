package com.cefasbysoftps.addplayer.core.datastore

class LocalRepository(private val dao: ReportDao) {


    suspend fun getReports(): List<ReportEntity> {
        return dao.getAll()
    }

    suspend fun saveReport(reportEntity: ReportEntity) {
        dao.insert(
            reportEntity
        )
    }

}
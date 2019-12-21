package com.example.qrcode.roomdb.utils

import com.example.qrcode.roomdb.QrResult.QrResults
import java.util.*

class DbHelper(var qrResultDataBase: LocaleDataBase) : DbHelperI {

    override fun insertQRResult(result: String): Int {
        val time = Calendar.getInstance()
        val resultType = findResultType(result)
        val qrResult = QrResults(result = result, resultType = resultType, calendar = time, favourite = false)
        return qrResultDataBase.getQrDao().insertQrResults(qrResult).toInt()
    }

    override fun getQRResult(id: Int): QrResults {
        return qrResultDataBase.getQrDao().getQrResults(id)
    }

    override fun addToFavourite(id: Int): Int {
        return qrResultDataBase.getQrDao().addToFavourite(id)
    }

    override fun removeFromFavourite(id: Int): Int {
        return qrResultDataBase.getQrDao().removeFromFavourite(id)
    }

    override fun deleteQrResults(id: Int): Int {
        return qrResultDataBase.getQrDao().deleteQrResults(id)
    }

    override fun getAllQRScannedResult(): List<QrResults> {
        return qrResultDataBase.getQrDao().getAllScannedResult()
    }

    override fun getAllFavouriteQRScannedResult(): List<QrResults> {
        return qrResultDataBase.getQrDao().getAllFavouriteResult()
    }

    override fun deleteAllQRScannedResult() {
        qrResultDataBase.getQrDao().deleteAllScannedResult()
    }

    override fun deleteAllFavouriteQRScannedResult() {
        qrResultDataBase.getQrDao().deleteAllFavouriteResult()
    }

    /*
    * This feature will add in future
    * */
    private fun findResultType(result: String): String {
        return "TEXT"
    }



}

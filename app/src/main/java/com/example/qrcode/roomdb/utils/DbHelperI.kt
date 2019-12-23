package com.example.qrcode.roomdb.utils

import com.example.qrcode.roomdb.QrResult.QrResults


interface DbHelperI {

    fun insertQRResult(result: String): Int

    fun getQRResult(id: Int): QrResults

    fun addToFavourite(id: Int): Int

    fun removeFromFavourite(id: Int): Int

    fun deleteQrResults(id: Int): Int

    fun deleteQrResult(id: Int): Int

    fun getAllQRScannedResult(): List<QrResults>

    fun getAllFavouriteQRScannedResult(): List<QrResults>

    fun deleteAllQRScannedResult()

    fun deleteAllFavouriteQRScannedResult()
}
package com.example.qrcode.utlis

import android.view.View
import java.text.SimpleDateFormat
import java.util.*



fun Calendar.toFormattedDisplay(): String {
    val simpleDateFormat = SimpleDateFormat("dd-mm-yyyy hh:mm a", Locale.US)
    return simpleDateFormat.format(this.time)
}
package com.example.qrcode.ui.dialog

import android.app.Dialog
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.text.ClipboardManager
import android.widget.Toast
import com.example.qrcode.R
import com.example.qrcode.roomdb.QrResult.QrResults
import com.example.qrcode.roomdb.utils.DbHelper
import com.example.qrcode.roomdb.utils.DbHelperI
import com.example.qrcode.roomdb.utils.LocaleDataBase
import com.example.qrcode.utlis.toFormattedDisplay
import kotlinx.android.synthetic.main.layout_qr_result_show.*

class QrCodeResultDialog(var context: Context) {

    private lateinit var dialog: Dialog

    private lateinit var dbHelperI: DbHelperI

    private var qrResult: QrResults? = null

    private var onDismissListener: OnDismissListener? = null

    init {
        init()
        initDialog()
    }


    private fun init() {
        dbHelperI = DbHelper(LocaleDataBase.getAppDatabase(context)!!)
    }

    private fun initDialog() {
        dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_qr_result_show)
        dialog.setCancelable(false)
        onClicks()
    }

    private fun onClicks() {

        dialog.favouriteIcon.setOnClickListener {
            if (it.isSelected) {
                removeFromFavourite()
            } else
                addToFavourite()
        }

        dialog.copyResult.setOnClickListener {
            copyResultToClipBoard()
        }

        dialog.shareResult.setOnClickListener {
            shareResult()
        }

        dialog.cancelDialog.setOnClickListener {
            dialog.dismiss()
            onDismissListener?.onDismiss()
        }
    }


    private fun addToFavourite() {
        dialog.favouriteIcon.isSelected = true
        dbHelperI.addToFavourite(qrResult?.id!!)
    }

    private fun removeFromFavourite() {
        dialog.favouriteIcon.isSelected = false
        dbHelperI.removeFromFavourite(qrResult?.id!!)
    }

    fun show(recentQrResults: QrResults) {
        this.qrResult = recentQrResults
        dialog.scannedDate.text = qrResult?.calendar?.toFormattedDisplay()
        dialog.scannedText.text = qrResult!!.result
        dialog.favouriteIcon.isSelected = qrResult!!.favourite
        dialog.show()
    }

    fun setOnDismissListener(dismissListener: OnDismissListener) {
        this.onDismissListener = dismissListener
    }

    private fun copyResultToClipBoard() {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("QrCodeScannedResult", dialog.scannedText.text)
        clipboard.text = clip.getItemAt(0).text.toString()
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun shareResult() {
        val txtIntent = Intent(Intent.ACTION_SEND)
        txtIntent.type = "text/plain"
        txtIntent.putExtra(
            Intent.EXTRA_TEXT,
            dialog.scannedText.text.toString()
        )
        context.startActivity(Intent.createChooser(txtIntent, "Share QR Result"))
    }

    interface OnDismissListener {
        fun onDismiss()
    }
}
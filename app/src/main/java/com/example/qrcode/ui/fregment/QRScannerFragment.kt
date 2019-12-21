package com.example.qrcode.ui.fregment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat

import com.example.qrcode.R
import com.example.qrcode.roomdb.utils.DbHelper
import com.example.qrcode.roomdb.utils.DbHelperI
import com.example.qrcode.roomdb.utils.LocaleDataBase
import com.example.qrcode.ui.dialog.QrCodeResultDialog
import kotlinx.android.synthetic.main.fragment_qrscanner.view.*
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

/**
 * A simple [Fragment] subclass.
 */
class QRScannerFragment : Fragment(), ZBarScannerView.ResultHandler {

    private lateinit var mView : View
    private lateinit var dbHelperI: DbHelperI
    private lateinit var scannerView: ZBarScannerView
    private lateinit var resultDialog: QrCodeResultDialog



    companion object{
        fun newInstance():QRScannerFragment{
            return QRScannerFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_qrscanner, container, false)
        init()
        onClicks()
        initViews()
        return mView.rootView
    }

    private fun init() {
        dbHelperI = DbHelper(LocaleDataBase.getAppDatabase(context!!)!!)
    }

    private fun initViews() {
        initializeQRCamera()
        setResultDialog()
    }

    private fun initializeQRCamera() {
        scannerView = ZBarScannerView(context)
        scannerView.setResultHandler(this)
        scannerView.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorTranslucent))
        scannerView.setBorderColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
        scannerView.setLaserColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
        scannerView.setBorderStrokeWidth(10)
        scannerView.setSquareViewFinder(true)
        scannerView.setupScanner()
        scannerView.setAutoFocus(true)
        startQRCamera()
        mView.containerScanner.addView(scannerView)
    }


    private fun onClicks() {
        mView.flashToggle.setOnClickListener {
            if (mView.flashToggle.isSelected) {
                offFlashLight()
            } else {
                onFlashLight()
            }
        }
    }

    private fun onFlashLight() {
        mView.flashToggle.isSelected = true
        scannerView.flash = true
    }

    private fun offFlashLight() {
        mView.flashToggle.isSelected = false
        scannerView.flash = false
    }

    private fun startQRCamera() {
        scannerView.startCamera()
    }

    private fun onQrResult(contents: String?) {
        if (contents.isNullOrEmpty())
            showToast("Empty Qr Result")
        else
            saveToDataBase(contents)
    }

    private fun showToast(message: String) {
        Toast.makeText(context!!, message, Toast.LENGTH_SHORT).show()
    }

    private fun setResultDialog() {
        resultDialog = QrCodeResultDialog(context!!)
        resultDialog.setOnDismissListener(object : QrCodeResultDialog.OnDismissListener {
            override fun onDismiss() {
                resetPreview()
            }
        })
    }

    private fun saveToDataBase(contents: String) {
        scannerView.stopCamera()
        val insertedResultId = dbHelperI.insertQRResult(contents)
        val qrResult = dbHelperI.getQRResult(insertedResultId)
        resultDialog.show(qrResult)

    }

    private fun resetPreview() {
        scannerView.stopCamera()
        scannerView.startCamera()
        scannerView.stopCameraPreview()
        scannerView.resumeCameraPreview(this)
    }


    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        onQrResult(rawResult?.contents)
        scannerView.resumeCameraPreview (this)
    }


}

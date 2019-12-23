package com.example.qrcode.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.qrcode.R
import com.example.qrcode.roomdb.QrResult.QrResults
import com.example.qrcode.roomdb.utils.DbHelperI
import com.example.qrcode.ui.dialog.QrCodeResultDialog
import com.example.qrcode.utlis.gone
import com.example.qrcode.utlis.toFormattedDisplay
import com.example.qrcode.utlis.visible
import kotlinx.android.synthetic.main.layout_single_item_qr_result.view.*

class ScannedResultListAdapter(
    var dbHelperI: DbHelperI,
    var context: Context,
    private var listOfScannedResult: MutableList<QrResults>
) :
    RecyclerView.Adapter<ScannedResultListAdapter.ScannedResultListViewHolder>() {

    private var resultDialog: QrCodeResultDialog =
        QrCodeResultDialog(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScannedResultListViewHolder {
        return ScannedResultListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_single_item_qr_result,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfScannedResult.size
    }

    override fun onBindViewHolder(holder: ScannedResultListViewHolder, position: Int) {
        holder.bind(listOfScannedResult[position])
    }

    inner class ScannedResultListViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        fun bind(qrResult: QrResults) {
            view.result.text = qrResult.result!!
            view.tvTime.text = qrResult.calendar.toFormattedDisplay()
            setFavourite(qrResult.favourite)
            onClicks(qrResult)
        }



        private fun setFavourite(isFavourite: Boolean) {
            if (isFavourite)
                view.favouriteIcon.visible()
            else
                view.favouriteIcon.gone()
        }


        private fun onClicks(qrResult: QrResults) {
            view.setOnClickListener {
                resultDialog.show(qrResult)
            }


        }




    }
}
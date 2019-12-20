package com.example.qrcode.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.qrcode.ui.fregment.QRScannerFragment
import com.example.qrcode.ui.fregment.ScannedHistoryFragment

class MainViewPagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                QRScannerFragment.newInstance()
            }
            1 -> {
                ScannedHistoryFragment.newInstance()
            }
            2 -> {
                ScannedHistoryFragment.newInstance()
            }
            else -> {
                QRScannerFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

}
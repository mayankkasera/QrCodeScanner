package com.example.qrcode.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.qrcode.R
import com.example.qrcode.roomdb.QrResult.QrResults
import com.example.qrcode.roomdb.utils.LocaleDataBase
import com.example.qrcode.ui.adapter.MainViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setViewPager()
        setBottomNavigationListener()
        setViewPagerListener()

        val qrResult = QrResults(result = "Text",resultType = "TEXT",favourite = false,calendar = Calendar.getInstance())
        LocaleDataBase.getAppDatabase(this)?.getQrDao()?.insertQrResults(qrResult)


    }

    private fun setViewPagerListener() {
        viewPager.setOnPageChangeListener(object :  ViewPager.SimpleOnPageChangeListener(){
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        bottomNavigationView.selectedItemId = R.id.qrScanMenuId
                    }
                    1 -> {
                        bottomNavigationView.selectedItemId = R.id.scannedResultMenuId
                    }
                    2 -> {
                        bottomNavigationView.selectedItemId = R.id.favouriteScannedMenuId
                    }
                }
            }
        })
    }

    private fun setBottomNavigationListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
               when(it.itemId){
                   R.id.qrScanMenuId -> viewPager.currentItem = 0
                   R.id.scannedResultMenuId -> viewPager.currentItem = 1
                   R.id.favouriteScannedMenuId -> viewPager.currentItem = 2
               }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setViewPager() {
        viewPager.adapter = MainViewPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = 2
    }


}

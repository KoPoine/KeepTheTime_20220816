package com.neppplus.keepthetime_20220816

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.keepthetime_20220816.adapters.MainViewPagerAdapter
import com.neppplus.keepthetime_20220816.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var mPagerAdapter: MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
//        바텀 네이게이션 메뉴 선택 이벤트 > 뷰페이져의 페이지 이동
        binding.bottomNav.setOnItemSelectedListener {
//            어떤 메뉴가 선택되었는가? > it 변수가 알려줌.
            when (it.itemId) {
                R.id.myAppointment -> binding.mainViewPager.currentItem = 0
                R.id.invitedAppointment -> binding.mainViewPager.currentItem = 1
                R.id.setting -> binding.mainViewPager.currentItem = 2
            }
            return@setOnItemSelectedListener true
        }
    }

    override fun setValues() {
        mPagerAdapter = MainViewPagerAdapter(this)
        binding.mainViewPager.adapter = mPagerAdapter
    }
}
package com.neppplus.keepthetime_20220816

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.neppplus.keepthetime_20220816.adapters.MainViewPagerAdapter
import com.neppplus.keepthetime_20220816.databinding.ActivityMainBinding
import com.neppplus.keepthetime_20220816.fragments.InvitedAppointmentFragment
import com.neppplus.keepthetime_20220816.fragments.MyAppointmentFragment
import com.neppplus.keepthetime_20220816.fragments.SettingFragment

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
//                R.id.myAppointment -> binding.mainViewPager.currentItem = 0
//                R.id.invitedAppointment -> binding.mainViewPager.currentItem = 1
//                R.id.setting -> binding.mainViewPager.currentItem = 2

                R.id.myAppointment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainViewPager, MyAppointmentFragment()).commit()
                }
                R.id.invitedAppointment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainViewPager, InvitedAppointmentFragment()).commit()
                }
                R.id.setting -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainViewPager, SettingFragment()).commit()
                }
            }
            return@setOnItemSelectedListener true
        }

//        뷰페이저 페이지 이동 > 바텀 네비게이션의 메뉴 선택

//        binding.mainViewPager.registerOnPageChangeCallback(
//            object : ViewPager2.OnPageChangeCallback(){
////                추상 메쏘드 X. 이벤트 처리 함수를 직접 오버라이딩
//
//                override fun onPageSelected(position: Int) {
//                    super.onPageSelected(position)
//
//                    binding.bottomNav.selectedItemId = when (position) {
//                        0 -> R.id.myAppointment
//                        1 -> R.id.invitedAppointment
//                        else -> R.id.setting
//                    }
//                }
//            })
    }

    override fun setValues() {
//        mPagerAdapter = MainViewPagerAdapter(this)
//        binding.mainViewPager.adapter = mPagerAdapter

        supportFragmentManager.beginTransaction()
            .add(R.id.mainViewPager, MyAppointmentFragment()).commit()
    }
}
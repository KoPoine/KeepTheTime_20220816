package com.neppplus.keepthetime_20220816

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.neppplus.keepthetime_20220816.adapters.FriendPager2Adapter
import com.neppplus.keepthetime_20220816.adapters.FriendPagerAdapter
import com.neppplus.keepthetime_20220816.databinding.ActivityMyFriendBinding

class MyFriendActivity : BaseActivity() {

    lateinit var binding : ActivityMyFriendBinding
//    lateinit var mPagerAdapter : FriendPagerAdapter
    lateinit var mPagerAdapter : FriendPager2Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_friend)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mPagerAdapter = FriendPager2Adapter(this)
        binding.friendViewPager.adapter = mPagerAdapter
//        binding.friendTabLayout.setupWithViewPager(binding.friendViewPager)

//        ViewPager2 + TabLayout의 결합 코드
//        파라미터 (TabLayout의 변수, ViewPager2의 변수)
        TabLayoutMediator(binding.friendTabLayout, binding.friendViewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "내 친구 목록"
                else -> tab.text = "친구 요청 확인"
            }
        }.attach()
    }
}
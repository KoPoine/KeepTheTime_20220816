package com.neppplus.keepthetime_20220816

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.keepthetime_20220816.adapters.FriendPagerAdapter
import com.neppplus.keepthetime_20220816.databinding.ActivityMyFriendBinding

class MyFriendActivity : BaseActivity() {

    lateinit var binding : ActivityMyFriendBinding
    lateinit var mPagerAdapter : FriendPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_friend)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mPagerAdapter = FriendPagerAdapter(supportFragmentManager)
        binding.friendViewPager.adapter = mPagerAdapter
        binding.friendTabLayout.setupWithViewPager(binding.friendViewPager)
    }
}
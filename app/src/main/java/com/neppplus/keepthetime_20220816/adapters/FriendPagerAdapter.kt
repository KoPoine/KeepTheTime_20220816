package com.neppplus.keepthetime_20220816.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.keepthetime_20220816.fragments.MyFriendFragment
import com.neppplus.keepthetime_20220816.fragments.RequestedFriendFragment

class FriendPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MyFriendFragment()
            else -> RequestedFriendFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "내 친구 목록"
            else -> "친구 요청 확인"
        }
    }
}
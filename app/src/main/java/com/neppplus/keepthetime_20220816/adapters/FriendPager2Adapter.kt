package com.neppplus.keepthetime_20220816.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neppplus.keepthetime_20220816.fragments.MyFriendFragment
import com.neppplus.keepthetime_20220816.fragments.RequestedFriendFragment

class FriendPager2Adapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MyFriendFragment()
            else -> RequestedFriendFragment()
        }
    }
}
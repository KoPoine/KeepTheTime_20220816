package com.neppplus.keepthetime_20220816.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.keepthetime_20220816.R
import com.neppplus.keepthetime_20220816.adapters.FriendRecyclerViewAdapter
import com.neppplus.keepthetime_20220816.databinding.FragmentMyFriendsBinding
import com.neppplus.keepthetime_20220816.datas.BasicResponse
import com.neppplus.keepthetime_20220816.datas.UserData
import com.neppplus.keepthetime_20220816.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFriendFragment: BaseFragment() {

    lateinit var binding : FragmentMyFriendsBinding
    lateinit var mFriendAdapter : FriendRecyclerViewAdapter
    val mFriendsList = ArrayList<UserData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_my_friends, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mFriendAdapter = FriendRecyclerViewAdapter(mContext, mFriendsList)
        binding.myFriendRecyclerView.adapter = mFriendAdapter
        binding.myFriendRecyclerView.layoutManager = LinearLayoutManager(mContext)

        getFriendDataFromServer()
    }

    fun getFriendDataFromServer() {
        val token = ContextUtil.getLoginToken(mContext)
        apiList.getRequestFriendList(token, "my")
            .enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    mFriendsList.clear()

                    val br = response.body()!!

                    mFriendsList.addAll(br.data.friends)
                    mFriendAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })



    }

}
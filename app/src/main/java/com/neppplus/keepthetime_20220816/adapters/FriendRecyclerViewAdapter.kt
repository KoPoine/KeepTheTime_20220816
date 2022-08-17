package com.neppplus.keepthetime_20220816.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.keepthetime_20220816.MyFriendActivity
import com.neppplus.keepthetime_20220816.R
import com.neppplus.keepthetime_20220816.datas.UserData
import com.neppplus.keepthetime_20220816.fragments.RequestedFriendFragment

// 내 친구 목록 & 요청 받은 목록 두 가지 동시표현하는 RecyclerViewAdapter

class FriendRecyclerViewAdapter(
    val mContext : Context, val mList : List<UserData>, var type : String
) : RecyclerView.Adapter<FriendRecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(item : UserData){

//            공통적으로 진행되는 UI 반영 부분
            val profileImg = itemView.findViewById<ImageView>(R.id.profileImg)
            val nickTxt = itemView.findViewById<TextView>(R.id.nickTxt)
            val emailTxt = itemView.findViewById<TextView>(R.id.emailTxt)
            val buttonLayout = itemView.findViewById<LinearLayout>(R.id.buttonLayout)
            val positiveBtn = itemView.findViewById<Button>(R.id.positiveBtn)
            val negativeBtn = itemView.findViewById<Button>(R.id.negativeBtn)

            Glide.with(mContext).load(item.profileImg).into(profileImg)
            nickTxt.text = item.nickname
            when (item.provider) {
                "facebook" -> {
                    emailTxt.text = "페북 로그인"
                }
                "kakao" -> {
                    emailTxt.text = "카카오 로그인"
                }
                "naver" -> {
                    emailTxt.text = "네이버 로그인"
                }
                else -> {
                    emailTxt.text = item.email
                }
            }

//            별개로 진행되어야하는 부분
            if (type == "request") {
                buttonLayout.visibility = View.VISIBLE
            }

            positiveBtn.setOnClickListener {
                (mContext as MyFriendActivity).apiList.
            }
            negativeBtn.setOnClickListener {  }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.friend_list_itme, parent, false)
        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}
package com.neppplus.keepthetime_20220816.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.keepthetime_20220816.R
import com.neppplus.keepthetime_20220816.SearchUserActivity
import com.neppplus.keepthetime_20220816.api.APIList
import com.neppplus.keepthetime_20220816.api.ServerAPI
import com.neppplus.keepthetime_20220816.datas.BasicResponse
import com.neppplus.keepthetime_20220816.datas.UserData
import com.neppplus.keepthetime_20220816.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// [도전과제] SearchUserRecyclerViewAdapter & FriendRecyclerViewAdapter 합쳐보자

class SearchUserRecyclerViewAdapter(
    val mContext : Context, val mList : List<UserData>
) : RecyclerView.Adapter<SearchUserRecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(item : UserData){

            val profileImg = itemView.findViewById<ImageView>(R.id.profileImg)
            val nickTxt = itemView.findViewById<TextView>(R.id.nickTxt)
            val emailTxt = itemView.findViewById<TextView>(R.id.emailTxt)
            val addFriendBtn = itemView.findViewById<Button>(R.id.addFriendBtn)

            addFriendBtn.visibility = View.VISIBLE
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

            addFriendBtn.setOnClickListener {
//                어뎁터에서 API 통신을 하기 위한 방법
//                1. 어댑터에서 API 새로 객체화 하기
//                val retrofit = ServerAPI.getRetrofit()
//                val apiList = retrofit.create(APIList::class.java)

//                2. context를 통해서 Activity에서 받아오기
                val token = ContextUtil.getLoginToken(mContext)
                (mContext as SearchUserActivity).apiList.postRequestAddFriend(token, item.id).enqueue(object : Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                mContext,
                                "${item.nickname}님께 친구요청을 보냈습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else {
                            val errorBodyStr = response.errorBody()!!.string()
                            val jsonObj = JSONObject(errorBodyStr)
                            val code = jsonObj.getInt("code")
                            val message = jsonObj.getString("message")

                            if (code == 400) {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })

            }

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
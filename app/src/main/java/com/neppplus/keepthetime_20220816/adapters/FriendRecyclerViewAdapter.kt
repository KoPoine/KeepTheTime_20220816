package com.neppplus.keepthetime_20220816.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.keepthetime_20220816.MyFriendActivity
import com.neppplus.keepthetime_20220816.R
import com.neppplus.keepthetime_20220816.datas.BasicResponse
import com.neppplus.keepthetime_20220816.datas.UserData
import com.neppplus.keepthetime_20220816.fragments.RequestedFriendFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

//            수락 / 거절 버튼 둘다 하는일이 동일 => type에 들어갈 값만 다르다.
//            버튼에 type을 정해놓고(tag) 인터페이스로 가져와서 사용하자.
            val ocl = object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val type = p0!!.tag.toString()
                    (mContext as MyFriendActivity).apiList.putRequestAddFriend(
                        item.id, type
                    ).enqueue(object : Callback<BasicResponse>{
                        override fun onResponse(
                            call: Call<BasicResponse>,
                            response: Response<BasicResponse>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    mContext,
                                    "${item.nickname}님의 친구 추가 요청을 ${type}하였습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                        }
                    })

//                requestFriendList를 갱신
//                프래그먼트의 친구요청목록(getRequestFriendListFromServer) 함수 실행?

//                ViewPager2에서 fragment를 찾아 리스트 갱신
//                ViewPager2에서는 내부의 fragment를 tag("f"+index)값을 통해서 찾아올예정
                    ((mContext as MyFriendActivity).supportFragmentManager
                        .findFragmentByTag("f1") as RequestedFriendFragment)
                        .getRequestFriendListFromServer()
                }
            }
            positiveBtn.setOnClickListener(ocl)
            negativeBtn.setOnClickListener(ocl)
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
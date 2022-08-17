package com.neppplus.keepthetime_20220816.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.keepthetime_20220816.MyPlaceActivity
import com.neppplus.keepthetime_20220816.MyPlaceDetailActivity
import com.neppplus.keepthetime_20220816.R
import com.neppplus.keepthetime_20220816.datas.BasicResponse
import com.neppplus.keepthetime_20220816.datas.PlaceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceRecyclerAdapter(
    val mContext : Context, val mList : List<PlaceData>
):RecyclerView.Adapter<PlaceRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(item : PlaceData) {

            val myPlaceTxt = itemView.findViewById<TextView>(R.id.myPlaceTxt)
            val primaryTxt = itemView.findViewById<TextView>(R.id.primaryTxt)

            myPlaceTxt.text = item.name
            if (item.isPrimary) {
                primaryTxt.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                val myIntent = Intent(mContext, MyPlaceDetailActivity::class.java)
                myIntent.putExtra("placeData",item)
                mContext.startActivity(myIntent)
            }

            itemView.setOnClickListener {
//                기본 값 변경 이벤트
                (mContext as MyPlaceActivity).apiList
                    .patchRequestEditPlace(
                        item.id
                    ).enqueue(object : Callback<BasicResponse>{
                        override fun onResponse(
                            call: Call<BasicResponse>,
                            response: Response<BasicResponse>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(mContext, "기본 출발 장소가 변경되었습니다.", Toast.LENGTH_SHORT)
                                    .show()
                                mContext.getPlaceListFromServer()
                            }
                        }

                        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                        }
                    })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.place_list_item, parent, false)
        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}
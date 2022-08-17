package com.neppplus.keepthetime_20220816

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.keepthetime_20220816.adapters.PlaceRecyclerAdapter
import com.neppplus.keepthetime_20220816.databinding.ActivityMyPlaceBinding
import com.neppplus.keepthetime_20220816.datas.BasicResponse
import com.neppplus.keepthetime_20220816.datas.PlaceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPlaceActivity : BaseActivity() {

    lateinit var binding : ActivityMyPlaceBinding
    lateinit var mPlaceAdapter : PlaceRecyclerAdapter
    val mPlaceList = ArrayList<PlaceData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_place)
        setupEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        getPlaceListFromServer()
    }

    override fun setupEvents() {
        binding.addPlaceBtn.setOnClickListener {
            val myIntent = Intent(mContext, AddMyPlaceActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        mPlaceAdapter = PlaceRecyclerAdapter(mContext, mPlaceList)
        binding.placeRecyclerView.adapter = mPlaceAdapter
        binding.placeRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun getPlaceListFromServer() {
        apiList.getRequestUserPlace().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!

                    mPlaceList.clear()
                    mPlaceList.addAll(br.data.places)
                    mPlaceAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }
}
package com.neppplus.keepthetime_20220816.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.keepthetime_20220816.AddAppointmentActivity
import com.neppplus.keepthetime_20220816.R
import com.neppplus.keepthetime_20220816.adapters.AppointmentRecyclerAdapter
import com.neppplus.keepthetime_20220816.databinding.FragmentMyAppointmentBinding
import com.neppplus.keepthetime_20220816.datas.AppointmentData
import com.neppplus.keepthetime_20220816.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAppointmentFragment : BaseFragment() {

    lateinit var binding : FragmentMyAppointmentBinding

    lateinit var mAppointAdapter : AppointmentRecyclerAdapter
    val mAppointList = ArrayList<AppointmentData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_appointment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        getAppointmentDataFromServer()
    }

    override fun setupEvents() {
        binding.addAppointBtn.setOnClickListener {
            val myIntent = Intent(mContext, AddAppointmentActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        mAppointAdapter = AppointmentRecyclerAdapter(mContext, mAppointList)
        binding.appointmentRecyclerView.adapter = mAppointAdapter
        binding.appointmentRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun getAppointmentDataFromServer() {
        apiList.getRequestMyAppointment().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    mAppointList.clear()

                    mAppointList.addAll(response.body()!!.data.appointments)

                    mAppointAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }
}
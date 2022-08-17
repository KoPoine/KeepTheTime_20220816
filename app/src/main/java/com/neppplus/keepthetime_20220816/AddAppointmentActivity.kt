package com.neppplus.keepthetime_20220816

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.keepthetime_20220816.databinding.ActivityAddAppointmentBinding
import com.neppplus.keepthetime_20220816.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddAppointmentActivity : BaseActivity() {

    lateinit var binding : ActivityAddAppointmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_appointment)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.saveBtn.setOnClickListener {
//            1. 약속의 제목을 정했는가?
            val inputTitle = binding.titleEdt.text.toString()
            if (inputTitle.isBlank()) {
                Toast.makeText(mContext, "약속 제목을 정해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            2. 약속 일시를 지정했는가?
//            => 날짜 / 시간 중 하나라도 선택하지 않았다면 별도 토스트 출력
            if (binding.dateTxt.text == "날짜 선택") {
                Toast.makeText(mContext, "날짜를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.timeTxt.text == "시간 선택") {
                Toast.makeText(mContext, "시간을 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            지금 시간과 선택된 시간과의 계산


//            3. 도착 지점을 선택했는가?
//            1) 약속 장소명을 기록 했는가?
            val inputPlaceName = binding.placeNameEdt.text.toString()
            if (inputPlaceName.isBlank()) {
                Toast.makeText(mContext, "약속 장소명을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            2) 실제 도착 지점을 선택했는가? => 네이버 지도 맵에서 도착 장소를 지정 했는가?

//            apiList.postRequestAddAppointment(
//                inputTitle, , inputPlaceName,
//            ).enqueue(object : Callback<BasicResponse>{
//                override fun onResponse(
//                    call: Call<BasicResponse>,
//                    response: Response<BasicResponse>
//                ) {
//
//                }
//
//                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
//
//                }
//            })

        }
    }

    override fun setValues() {

    }
}
package com.neppplus.keepthetime_20220816

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.neppplus.keepthetime_20220816.adapters.PlaceSpinnerAdapter
import com.neppplus.keepthetime_20220816.databinding.ActivityAddAppointmentBinding
import com.neppplus.keepthetime_20220816.datas.BasicResponse
import com.neppplus.keepthetime_20220816.datas.PlaceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddAppointmentActivity : BaseActivity() {

    lateinit var binding : ActivityAddAppointmentBinding

//    약속 장소를 담고있는 관련 변수
    var mSelectedLatLng : LatLng? = null

//    선택된 약속 일시를 저장할 변수
    var mSelectedDateTime = Calendar.getInstance() // 기본값 : 현재 시간

//    출발 장소 관련 변수
    lateinit var mPlaceSpinnerAdapter : PlaceSpinnerAdapter
    val mStartPlaceList = ArrayList<PlaceData>()
    lateinit var mStartPlace : PlaceData

//    네이버 지도 관련 변수
    var mNaverMap : NaverMap? = null
    var mStartPlaceMarker = Marker()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_appointment)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        var serverDateTime = ""

//        날짜 선택
        binding.dateTxt.setOnClickListener {
            val dl = object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, date: Int) {

//                    날짜가 선택되고 나면 진행 할 일을 작성
                    Log.d("선택된 시간", "${year}년, ${month}월, ${date}일")

                    mSelectedDateTime.set(year, month, date)
                    val sdf = SimpleDateFormat("yyyy. M. dd (E)")
                    Log.d("변환된 시간", sdf.format(mSelectedDateTime.time))

                    binding.dateTxt.text = sdf.format(mSelectedDateTime.time)
                }
            }



//            DatePickerDialog 팝업
            val dpd = DatePickerDialog(
                mContext,
                dl,
                mSelectedDateTime.get(Calendar.YEAR),
                mSelectedDateTime.get(Calendar.MONTH),
                mSelectedDateTime.get(Calendar.DAY_OF_MONTH),
            ).show()
        }

//        시간 선택
        binding.timeTxt.setOnClickListener {
            val tl = object : TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
                    Log.d("선택된 시간", "${hour}시, ${minute}분")

                    mSelectedDateTime.set(Calendar.HOUR_OF_DAY, hour)
                    mSelectedDateTime.set(Calendar.MINUTE, minute)

                    val sdf = SimpleDateFormat("a h:mm")
                    binding.timeTxt.text = sdf.format(mSelectedDateTime.time)
                }
            }

            val tpd = TimePickerDialog(
                mContext,
                tl,
                mSelectedDateTime.get(Calendar.HOUR),
                mSelectedDateTime.get(Calendar.MINUTE),
                false
            ).show()
        }

//        시작장소 스피너 선택 이벤트
        binding.startPlaceSpinner
            .onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, postion: Int, p3: Long) {
//                Log.d("Spinner 변수 값", "p2 : $postion, p3 : $p3")
                mStartPlace = mStartPlaceList[postion]

                Log.d("출발장소", mStartPlace.toString())

//                네이버 지도에 출발지 표시
                mNaverMap?.let {
                    mStartPlaceMarker.position = LatLng(mStartPlace.latitude, mStartPlace.longitude)
                    mStartPlaceMarker.map = mNaverMap

                    Log.d("출발장소", mStartPlace.toString())

                    val cameraUpdate = CameraUpdate
                        .scrollTo(LatLng(mStartPlace.latitude, mStartPlace.longitude))
                    mNaverMap!!.moveCamera(cameraUpdate)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

//        약속 등록 이벤트
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
            if (mSelectedDateTime.timeInMillis < Calendar.getInstance().timeInMillis) {
                Toast.makeText(mContext, "현재 시간 이후의 시간으로 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            3. 도착 지점을 선택했는가?
//            1) 약속 장소명을 기록 했는가?
            val inputPlaceName = binding.placeNameEdt.text.toString()
            if (inputPlaceName.isBlank()) {
                Toast.makeText(mContext, "약속 장소명을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            2) 실제 도착 지점을 선택했는가? => 네이버 지도 맵에서 도착 장소를 지정 했는가?
            if (mSelectedLatLng == null) {
                Toast.makeText(mContext, "약속 장소를 지도에서 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")

            apiList.postRequestAddAppointment(
                inputTitle, sdf.format(mSelectedDateTime.time), inputPlaceName,
                mSelectedLatLng!!.latitude, mSelectedLatLng!!.longitude,
                mStartPlace.name, mStartPlace.latitude, mStartPlace.longitude
            ).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    Toast.makeText(mContext, "약속이 등록되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
        }

//        지도 영역에 손을 대면(setOnTouch) => 스크롤 뷰를 정지
//        대안 : 지도 위에 텍스트뷰를 겹쳐두고, 텍스트뷰에 손을 대면 => 스크롤뷰를 정지
        binding.scrollHelpTxt.setOnTouchListener { view, motionEvent ->

            binding.scrollView.requestDisallowInterceptTouchEvent(true)

//            터치 이벤트만 먹히게 할거냐? => no / 뒤에 있는 지도 동작도 같이 실행
            return@setOnTouchListener false
        }

    }

    override fun setValues() {

        mPlaceSpinnerAdapter = PlaceSpinnerAdapter(mContext, R.layout.place_list_item, mStartPlaceList)
        binding.startPlaceSpinner.adapter = mPlaceSpinnerAdapter

        getStartPlaceDataFromServer()

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync {

            if (mNaverMap == null) {
                mNaverMap = it
            }

            val marker = Marker()
            marker.icon = OverlayImage.fromResource(R.drawable.red_marker)

            mNaverMap!!.setOnMapClickListener { pointF, latLng ->
                mSelectedLatLng = latLng
                marker.position = latLng
                marker.map = mNaverMap
            }
        }
    }

    fun getStartPlaceDataFromServer() {
        apiList.getRequestUserPlace().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {

                    mStartPlaceList.clear()
                    mStartPlaceList.addAll(response.body()!!.data.places)
                    mPlaceSpinnerAdapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }
}
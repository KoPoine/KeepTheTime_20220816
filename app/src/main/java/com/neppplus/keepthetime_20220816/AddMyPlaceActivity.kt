package com.neppplus.keepthetime_20220816

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Marker
import com.neppplus.keepthetime_20220816.databinding.ActivityAddMyPlaceBinding
import com.neppplus.keepthetime_20220816.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddMyPlaceActivity : BaseActivity() {

    lateinit var binding : ActivityAddMyPlaceBinding

//    좌표 관련 멤버변수
    var mSelectedLatitude = 37.5779235853308
    var mSelectedLongitude = 127.033553463647

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_my_place)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.saveBtn.setOnClickListener {
//            저장 : 분기처리 (이름을 지정 하였나?)
            val inputName = binding.placeNameEdt.text.toString()
            if (inputName.isBlank()) {
                Toast.makeText(mContext, "이름은 공백일 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            apiList.postRequestAddUserPlace(
                inputName, mSelectedLatitude, mSelectedLongitude, "false"
            ).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(mContext, "장소 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    
                }
            })
        }
    }

    override fun setValues() {
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

//        네이버 맵 객체화
        mapFragment.getMapAsync {

//            지도 로딩이 끝난후, 얻어낸 온전한 지도 객체 => 변수화
            val naverMap = it

            val coord = LatLng(37.5779235853308, 127.033553463647)

            val cameraPosition = CameraPosition(coord, 16.0)

//            coord에 설정한 좌표(기본 좌표 => 학원 좌표값) > 네이버 지도의 카메라 이동
            val cameraUpdate = CameraUpdate.scrollTo(coord)
//            naverMap.moveCamera(cameraUpdate)
            naverMap.cameraPosition = cameraPosition

//            마커 생성 => 위치 : 기본 좌표(coord)
            val marker = Marker()
            marker.position = coord
            marker.map = naverMap

            naverMap.setOnMapClickListener { pointF, latLng ->
                Log.d("클릭된 위/경도", "위도 : ${latLng.latitude}, 경도 : ${latLng.longitude}")

                marker.position = latLng
                marker.map = naverMap

                mSelectedLatitude = latLng.latitude
                mSelectedLongitude = latLng.longitude
            }
        }
    }


}
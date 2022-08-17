package com.neppplus.keepthetime_20220816

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment

class AddMyPlaceActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_my_place)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

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


            val cameraPosition = CameraPosition(coord, 20.0)

//            coord에 설정한 좌표(기본 좌표 => 학원 좌표값) > 네이버 지도의 카메라 이동
            val cameraUpdate = CameraUpdate.scrollTo(coord)
//            naverMap.moveCamera(cameraUpdate)
            naverMap.cameraPosition = cameraPosition
        }
    }


}
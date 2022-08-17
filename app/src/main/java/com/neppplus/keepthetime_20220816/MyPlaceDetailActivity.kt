package com.neppplus.keepthetime_20220816

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.MapView
import com.naver.maps.map.overlay.Marker
import com.neppplus.keepthetime_20220816.databinding.ActivityMyPlaceDetailBinding
import com.neppplus.keepthetime_20220816.datas.PlaceData

class MyPlaceDetailActivity : BaseActivity() {

    lateinit var binding : ActivityMyPlaceDetailBinding
    lateinit var mapView : MapView

    lateinit var placeData : PlaceData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_place_detail)
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        placeData = intent.getSerializableExtra("placeData") as PlaceData

        binding.placeNameTxt.text = placeData.name
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.mapView) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.mapView, it).commit()
            }

        mapFragment.getMapAsync {
            val naverMap = it

            val coord = LatLng(placeData.latitude, placeData.longitude)

            val cameraUpdate = CameraUpdate.scrollTo(coord)
            naverMap.moveCamera(cameraUpdate)

            val marker = Marker()
            marker.position = coord
            marker.map = naverMap
        }
    }
}
package com.neppplus.keepthetime_20220816.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.neppplus.keepthetime_20220816.R
import com.neppplus.keepthetime_20220816.databinding.FragmentSettingBinding
import com.neppplus.keepthetime_20220816.datas.BasicResponse
import com.neppplus.keepthetime_20220816.utils.ContextUtil
import com.neppplus.keepthetime_20220816.utils.GlobalData
import com.neppplus.keepthetime_20220816.utils.URIPathHelper
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SettingFragment : BaseFragment() {

    lateinit var binding : FragmentSettingBinding

    private val REQ_FOR_GALLERY = 1000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        프로필 이미지 변경 이벤트 처리

        binding.profileImg.setOnClickListener {

//            이미지 조회 권한 확인 (Read_External_Storage)
            val pl = object : PermissionListener{
                override fun onPermissionGranted() {
//                    [도전 과제] 갤러리 외에 카메라로 찍은 사진을 업로드하는 방법
//                    Intent(3) 활용하여 다른화면에서 이미지를 가져오는 로직(다른화면 > 안드로이드에서 제공하는 화면)
                    val myIntent = Intent()
                    myIntent.action = Intent.ACTION_PICK
                    myIntent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
                    startActivityForResult(myIntent, REQ_FOR_GALLERY)
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(mContext, "갤러리 조회 권한이 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            TedPermission.create()
                .setPermissionListener(pl)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check()

        }

    }

    override fun setValues() {
        Glide.with(mContext)
            .load(GlobalData.loginUser!!.profileImg)
            .into(binding.profileImg)
        binding.nickTxt.text = GlobalData.loginUser!!.nickname
        binding.readyMinuteTxt.text = "${GlobalData.loginUser!!.readyMinute}분"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_FOR_GALLERY) {

//                data? 선택된 사진에 대한 정보를 가지고 있다.

                val selectedImgUri = data?.data!!  // 선택한 사진에 찾아갈 Uri 경로 받아내기

//                가져온 사진을 이미지뷰에 반영
//                Glide.with(mContext).load(selectedImgUri).into(binding.profileImg)

//                Uri -> 실제 첨부 가능한 파일 형태로 변환 (File객체를 실제 Path를 통해서 만든다.)
                val file = File(URIPathHelper().getPath(mContext, selectedImgUri))

//                완성된 파일을, Retrofit에 첨부 가능한 RequestBody 형태로 가공
                val fileReqBody = RequestBody.create(MediaType.get("image/*"), file)

//                파일이 같이 첨부되는 API통신은 Multipart 형태로 모든 데이터를 첨부해야함.
                val multiPartBody =
                    MultipartBody.Part.
                    createFormData("profile_image", "myProfile.jpg", fileReqBody)


//                실제 서버에 완성된 데이터 전송
                val token = ContextUtil.getLoginToken(mContext)
                apiList.putRequestUserImg(
                    token, multiPartBody
                ).enqueue(object : Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(mContext, "프로필 사진이 변경되었습니다.", Toast.LENGTH_SHORT).show()
                            GlobalData.loginUser = response.body()!!.data.user
                            Glide.with(mContext).load(selectedImgUri).into(binding.profileImg)
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })

            }
        }
    }
}
package com.neppplus.keepthetime_20220816.fragments

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.neppplus.keepthetime_20220816.*
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

//        닉네임 변경 이벤트 처리
        binding.nickTxt.setOnClickListener {
            val customView = LayoutInflater.from(mContext).inflate(R.layout.custom_alert_dialog, null)

            val alert = AlertDialog.Builder(mContext)
                .setTitle("닉네임 변경")
                .setView(customView)
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
//                    customView에 있는 inputEdt를 찾아와서 그 내용값을 inputNick에 담아서 서버로 전달
//                    1. customView에 있는 inputEdt 찾아오기
                    val inputEdt = customView.findViewById<EditText>(R.id.inputEdt)

//                    2. inputNick 변수 생성 > EditText값을 대입 (inputNick이 공백일 경우 toast 전달 및 ClickListener 탈출)
                    val inputNick = inputEdt.text.toString()

                    apiList.patchRequestEditUserData("nickname", inputNick)
                        .enqueue(object : Callback<BasicResponse> {
                            override fun onResponse(
                                call: Call<BasicResponse>,
                                response: Response<BasicResponse>
                            ) {
                                if (response.isSuccessful) {
                                    Toast.makeText(mContext, "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT)
                                        .show()
                                    GlobalData.loginUser = response.body()!!.data.user

                                    binding.nickTxt.text = GlobalData.loginUser!!.nickname
                                }

                            }

                            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                            }
                        })
                })
                .setNegativeButton("취소", null)
                .show()
        }

//        [응용 문제] 준비 시간 변경 이벤트 처리
        binding.changeReadyMinuteLayout.setOnClickListener {
//            [도전 과제] 사용자가 입력한 준비 시간이 만약 60분이 넘을 경우 -> ~시간 ~분으로 표시
        }

//        비밀번호 변경 이벤트 처리
        binding.changePwLayout.setOnClickListener {
            val customView = LayoutInflater.from(mContext).inflate(R.layout.custom_alert_dialog, null)

            val inputEdt = customView.findViewById<EditText>(R.id.inputEdt)
            val passwordLayout = customView.findViewById<LinearLayout>(R.id.passwordLayout)
            val currentPwEdt = customView.findViewById<EditText>(R.id.currentPwEdt)
            val newPwEdt = customView.findViewById<EditText>(R.id.newPwEdt)

            passwordLayout.visibility = View.VISIBLE
            inputEdt.visibility = View.GONE

            val alert = AlertDialog.Builder(mContext)
                .setTitle("비밀번호 변경")
                .setMessage("비밀번호 변경을 위해서는\n현재 비밀번호가 일치해야 합니다.")
                .setView(customView)
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
//                    비밀번호 EditText가 들어있는 Layout의 visibility & 기존의 EditText의 visibility 변경

                    val currentPw = currentPwEdt.text.toString()
                    val newPw = newPwEdt.text.toString()

                    apiList.patchRequestChangePassword(currentPw, newPw)
                        .enqueue(object : Callback<BasicResponse> {
                            override fun onResponse(
                                call: Call<BasicResponse>,
                                response: Response<BasicResponse>
                            ) {
                                if (response.isSuccessful) {
                                    Toast.makeText(mContext, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT)
                                        .show()

                                    ContextUtil.setLoginToken(mContext, response.body()!!.data.token)
                                }

                            }

                            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                            }
                        })
                })
                .setNegativeButton("취소", null)
                .show()
        }

//        로그아웃
        binding.logoutLayout.setOnClickListener {

            val customView = LayoutInflater.from(mContext).inflate(R.layout.custom_alert_dialog, null)

            val positiveBtn = customView.findViewById<Button>(R.id.positiveBtn)
            val negativeBtn = customView.findViewById<Button>(R.id.negativeBtn)

            val alert = AlertDialog.Builder(mContext)
                .setMessage("로그아웃 하시겠습니까?")
                .setView(customView)
                .create()

            positiveBtn.setOnClickListener {
                Toast.makeText(mContext, "확인", Toast.LENGTH_SHORT).show()
            }
            negativeBtn.setOnClickListener {
                Toast.makeText(mContext, "취소", Toast.LENGTH_SHORT).show()
                alert.dismiss()
            }

            alert.show()


//            ContextUtil.setLoginToken(mContext, "")
//
//            GlobalData.loginUser = null
//
//            val myIntent = Intent(mContext, LoginActivity::class.java)
//            myIntent.flags =
//                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(myIntent)
//
////            Fragment에서는 기존의 Activity를 종료할 수 없다.(Activity를 찾아와야...=> requireActivity())
//            requireActivity().finish()

//            requireActivity().finishAffinity()
        }

//        친구 목록 관리
        binding.editMyFriendLayout.setOnClickListener {
            val myIntent = Intent(mContext, MyFriendActivity::class.java)
            startActivity(myIntent)
        }

        binding.editMyPlaceLayout.setOnClickListener {
            val myIntent = Intent(mContext, MyPlaceActivity::class.java)
            startActivity(myIntent)
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
                apiList.putRequestUserImg(
                    multiPartBody
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
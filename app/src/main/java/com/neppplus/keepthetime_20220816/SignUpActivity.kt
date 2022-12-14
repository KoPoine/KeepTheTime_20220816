package com.neppplus.keepthetime_20220816

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.keepthetime_20220816.databinding.ActivitySignUpBinding
import com.neppplus.keepthetime_20220816.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {

    lateinit var binding : ActivitySignUpBinding

    var isEmailOk = false
    var isNickOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.signUpBtn.setOnClickListener {
//            1. 아이디 (이메일) 입력했는가?
            val inputEmail = binding.emailEdt.text.toString()
            if (inputEmail.isBlank()) {
                Toast.makeText(mContext, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            2. 비밀번호에 공백은 없는가?
            val inputPw = binding.passwordEdt.text.toString()
            if (inputPw.isBlank()) {
                Toast.makeText(mContext, "비밀번호는 빈칸 혹은 공백일 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            3. 비밀번호가 두개 모두 일치하는가?
            val checkPw = binding.checkPwEdt.text.toString()
            if (inputPw != checkPw) {
                Toast.makeText(mContext, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            4. 닉네임을 입력했는가?
            val inputNick = binding.nickEdt.text.toString()
            if (inputNick.isBlank()) {
                Toast.makeText(mContext, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            5. 이메일과 닉네임 모두 중복 체크를 했는가?
            if (!isEmailOk || !isNickOk) {
                Toast.makeText(mContext, "이메일 및 닉네임 중복 검사 진행해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            서버에 연결 (회원가입 진행)
            apiList.putRequestSignUp(
                inputEmail, inputPw, inputNick
            ).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        Toast.makeText(mContext, "~~님 가입을 축하합니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {

                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })


        }

//        이메일 중복 확인 버튼
        binding.emailDupBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            dupCheck("EMAIL", inputEmail)
        }

//        닉네임 중복 확인 버튼
        binding.nickDupBtn.setOnClickListener {
            val inputNick = binding.nickEdt.text.toString()
            dupCheck("NICK_NAME", inputNick)
        }
    }

    override fun setValues() {

    }

    fun dupCheck(type: String, value : String) {
        apiList.getRequestDupCheck(type, value).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    when(type) {
                        "EMAIL" -> {}
                        "NICK_NAME" -> {}
                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }
}
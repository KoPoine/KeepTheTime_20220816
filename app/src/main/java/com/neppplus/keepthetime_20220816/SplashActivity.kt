package com.neppplus.keepthetime_20220816

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.common.util.Utility
import com.neppplus.keepthetime_20220816.datas.BasicResponse
import com.neppplus.keepthetime_20220816.utils.ContextUtil
import com.neppplus.keepthetime_20220816.utils.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity() {

    var isTokenOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        val token = ContextUtil.getLoginToken(mContext)
        apiList.getRequestMyInfo().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    isTokenOk = true
                    GlobalData.loginUser = response.body()!!.data.user
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

    override fun setValues() {
        getKeyHash()

        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed(
            {

                val isAutoLogin = ContextUtil.getAutoLogin(mContext)

                if (isTokenOk && isAutoLogin) {
                    Toast.makeText(mContext, "${GlobalData.loginUser!!.nickname}님 환영합니다.", Toast.LENGTH_SHORT).show()
                    val myIntent = Intent(mContext, MainActivity::class.java)
                    startActivity(myIntent)
                } else {
                    val myIntent = Intent(mContext, LoginActivity::class.java)
                    startActivity(myIntent)
                }
                finish()
            }, 2000
        )
    }

    fun getKeyHash() {
        var keyHash = Utility.getKeyHash(mContext)

        Log.d("keyHash", keyHash)
    }
}
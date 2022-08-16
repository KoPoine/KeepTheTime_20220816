package com.neppplus.keepthetime_20220816

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.keepthetime_20220816.adapters.SearchUserRecyclerViewAdapter
import com.neppplus.keepthetime_20220816.databinding.ActivitySearchUserBinding
import com.neppplus.keepthetime_20220816.datas.BasicResponse
import com.neppplus.keepthetime_20220816.datas.UserData
import com.neppplus.keepthetime_20220816.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserActivity : BaseActivity() {

    lateinit var binding : ActivitySearchUserBinding
    lateinit var mUserAdapter : SearchUserRecyclerViewAdapter
    val mUserList = ArrayList<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_user)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.searchBtn.setOnClickListener {
            val inputNick = binding.searchEdt.text.toString()
            searchUserFromServer(inputNick)
        }
    }

    override fun setValues() {
        mUserAdapter = SearchUserRecyclerViewAdapter(mContext, mUserList)
        binding.searchUserRecyclerView.adapter = mUserAdapter
        binding.searchUserRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

//    [도전과제]
//      1. if 검색 결과가 없다면 => 검색 결과가 없습니다. TextView를 화면에 띄우자.
    fun searchUserFromServer(inputNick : String) {
        val token = ContextUtil.getLoginToken(mContext)
        apiList.getRequestSearchUser(
            token, inputNick
        ).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mUserList.clear()

                    mUserList.addAll(br.data.users)
                    mUserAdapter.notifyDataSetChanged()
                }
                else {
                    val errorBodyStr = response.errorBody()!!.string()
                    val jsonObj = JSONObject(errorBodyStr)
                    val message = jsonObj.getString("message")

                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}

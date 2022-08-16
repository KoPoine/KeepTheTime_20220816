package com.neppplus.keepthetime_20220816.datas

import com.google.gson.annotations.SerializedName

data class UserData(
    val id : Int,
    val email : String,
    @SerializedName("ready_minute")
    val readyMinute : Int,   // 서버에서 내려주는 변수명 : ready_minute
    @SerializedName("nick_name")
    val nickname : String,
    @SerializedName("profile_img")
    val profileImg : String,
    val provider : String
)

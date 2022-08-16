package com.neppplus.keepthetime_20220816.datas

data class DataResponse(
    val token : String,
    val user : UserData,
    val friends : List<UserData>,
    val users : List<UserData>
)

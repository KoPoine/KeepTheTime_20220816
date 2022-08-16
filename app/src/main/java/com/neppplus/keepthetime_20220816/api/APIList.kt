package com.neppplus.keepthetime_20220816.api

import com.neppplus.keepthetime_20220816.datas.BasicResponse
import retrofit2.Call
import retrofit2.http.*

interface APIList {

    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(
        @Field("email") email : String,
        @Field("password") pw : String,
        @Field("nick_name") nick : String
    ): Call<BasicResponse>

    @GET("/user/check")
    fun getRequestDupCheck(
        @Query("type") type : String,
        @Query("value") value : String
    ): Call<BasicResponse>

}
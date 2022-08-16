package com.neppplus.keepthetime_20220816.api

import com.neppplus.keepthetime_20220816.datas.BasicResponse
import okhttp3.MultipartBody
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

    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email") email : String,
        @Field("password") password : String
    ): Call<BasicResponse>

    @GET("/user")
    fun getRequestMyInfo(
        @Header("X-Http-Token") token: String
    ): Call<BasicResponse>

    @Multipart
    @PUT("/user/image")
    fun putRequestUserImg(
        @Header("X-Http-Token") token: String,
        @Part img : MultipartBody.Part
    ): Call<BasicResponse>

}
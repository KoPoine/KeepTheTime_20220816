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

    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestEditUserData(
        @Header("X-Http-Token") token: String,
        @Field("field") field: String,
        @Field("value") value: String
    ): Call<BasicResponse>

    @FormUrlEncoded
    @PATCH("/user/password")
    fun patchRequestChangePassword(
        @Header("X-Http-Token") token: String,
        @Field("current_password") currentPw: String,
        @Field("new_password") newPw: String
    ): Call<BasicResponse>

    @GET("/user/friend")
    fun getRequestFriendList(
        @Header("X-Http-Token") token: String,
        @Query("type") type : String
    ): Call<BasicResponse>

    @GET("/search/user")
    fun getRequestSearchUser(
        @Header("X-Http-Token") token: String,
        @Query("nickname") nickname : String
    ): Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/friend")
    fun postRequestAddFriend(
        @Header("X-Http-Token") token: String,
        @Field("user_id") id : Int
    ): Call<BasicResponse>
}
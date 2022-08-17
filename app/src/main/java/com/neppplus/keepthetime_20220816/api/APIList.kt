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
    ): Call<BasicResponse>

    @Multipart
    @PUT("/user/image")
    fun putRequestUserImg(
        @Part img : MultipartBody.Part
    ): Call<BasicResponse>

    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestEditUserData(
        @Field("field") field: String,
        @Field("value") value: String
    ): Call<BasicResponse>

    @FormUrlEncoded
    @PATCH("/user/password")
    fun patchRequestChangePassword(
        @Field("current_password") currentPw: String,
        @Field("new_password") newPw: String
    ): Call<BasicResponse>

    @GET("/user/friend")
    fun getRequestFriendList(
        @Query("type") type : String
    ): Call<BasicResponse>

    @GET("/search/user")
    fun getRequestSearchUser(
        @Query("nickname") nickname : String
    ): Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/friend")
    fun postRequestAddFriend(
        @Field("user_id") id : Int
    ): Call<BasicResponse>

    @FormUrlEncoded
    @PUT("/user/friend")
    fun putRequestAddFriend(
        @Field("user_id") id:Int,
        @Field("type") type : String
    ) : Call<BasicResponse>
}
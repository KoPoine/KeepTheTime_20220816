package com.neppplus.keepthetime_20220816.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class AppointmentData(
    val id : Int,
    val title : String,
    val datetime : Date,
    val place : String,
    val latitude : Double,
    val longitude : Double,
    @SerializedName("invited_friends")
    val invitedFriends : List<UserData>
) : Serializable

package com.neppplus.keepthetime_20220816.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlaceData(
    val id : Int,
    val name : String,
    val latitude : Double,
    val longitude : Double,
    @SerializedName("is_primary")
    val isPrimary : Boolean
) : Serializable
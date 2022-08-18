package com.neppplus.keepthetime_20220816.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.neppplus.keepthetime_20220816.R
import com.neppplus.keepthetime_20220816.datas.PlaceData

class PlaceSpinnerAdapter(
    val mContext: Context, val resId : Int, val mList : List<PlaceData>
) : ArrayAdapter<PlaceData>(mContext, resId, mList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if (tempRow == null) {
           tempRow = LayoutInflater.from(mContext).inflate(resId, null)
        }
        val row = tempRow!!
        val myPlaceTxt = row.findViewById<TextView>(R.id.myPlaceTxt)
        val primaryTxt = row.findViewById<TextView>(R.id.primaryTxt)

        myPlaceTxt.text = mList[position].name
        if (mList[position].isPrimary) {
            primaryTxt.visibility = View.VISIBLE
        }

        return row
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if (tempRow == null){
            tempRow = LayoutInflater.from(mContext).inflate(resId, null)
        }
        val row = tempRow!!
        val myPlaceTxt = row.findViewById<TextView>(R.id.myPlaceTxt)
        val primaryTxt = row.findViewById<TextView>(R.id.primaryTxt)
        myPlaceTxt.text = mList[position].name
        if (mList[position].isPrimary) {
            primaryTxt.visibility = View.VISIBLE
        }

        return row
    }

}
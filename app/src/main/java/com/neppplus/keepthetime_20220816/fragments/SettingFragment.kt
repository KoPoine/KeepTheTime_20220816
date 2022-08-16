package com.neppplus.keepthetime_20220816.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.keepthetime_20220816.R
import com.neppplus.keepthetime_20220816.databinding.FragmentSettingBinding
import com.neppplus.keepthetime_20220816.utils.GlobalData

class SettingFragment : BaseFragment() {

    lateinit var binding : FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        프로필 이미지 변경 이벤트 처리

        binding.profileImg.setOnClickListener {

        }

    }

    override fun setValues() {
        Glide.with(mContext)
            .load(GlobalData.loginUser!!.profileImg)
            .into(binding.profileImg)
        binding.nickTxt.text = GlobalData.loginUser!!.nickname
        binding.readyMinuteTxt.text = "${GlobalData.loginUser!!.readyMinute}분"
    }
}
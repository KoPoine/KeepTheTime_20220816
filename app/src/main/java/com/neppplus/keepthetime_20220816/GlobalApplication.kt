package com.neppplus.keepthetime_20220816

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "c9b397895bb99655112aca33aa636c30")

//        추가적으로 진행되는 기타 초기화 코드 작성
    }

}
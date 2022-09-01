package com.neppplus.keepthetime_20220816.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.TypedValue
import java.io.File

class SizeUtil {

    companion object {
        fun dpToPx(context: Context, dp: Float): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.resources.displayMetrics
            )
        }

//        파일 비트맵 변환 및 용량 축소
        fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
            outputStream().use { out ->
                bitmap.compress(format, quality, out)
                out.flush()
            }
        }

    }

}
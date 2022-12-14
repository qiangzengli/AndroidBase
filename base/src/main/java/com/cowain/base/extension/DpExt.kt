package com.cowain.base.extension

import android.content.res.Resources
import android.util.TypedValue

/**
 * dp , sp 转换扩展属性
 */

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

inline val Float.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )
val Int.dp get() = this.toFloat().dp
val Int.sp get() = this.toFloat().sp

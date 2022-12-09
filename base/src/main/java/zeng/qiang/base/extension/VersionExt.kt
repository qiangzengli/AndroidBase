package zeng.qiang.base.extension

import android.content.Context


val Context.versionName
    get() = packageManager.getPackageInfo(packageName, 0).versionName

val Context.versionCode
    get() = packageManager.getPackageInfo(packageName, 0).versionCode
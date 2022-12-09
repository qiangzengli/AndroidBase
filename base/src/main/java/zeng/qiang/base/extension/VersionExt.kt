package zeng.qiang.base.extension

import android.content.Context
import android.os.Build


val Context.versionName
    get() = packageManager.getPackageInfo(packageName, 0).versionName

val Context.versionCode: String
    get() {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            packageInfo.longVersionCode.toString()
        else
            packageInfo.versionName
    }
package zeng.qiang.base.extension

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build


val Context.packageInfo: PackageInfo
    get() = packageManager.getPackageInfo(packageName, 0)

val Context.versionName: String
    get() = packageInfo.versionName

val Context.versionCode: String
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) packageInfo.longVersionCode.toString()
    else packageInfo.versionName

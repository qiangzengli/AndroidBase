package com.cowain.base.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi

/**
 * Android 8.0 未知源安装权限设置
 */
@RequiresApi(Build.VERSION_CODES.O)
class UnknownSourceContract : ActivityResultContract<Unit?, Boolean>() {
    override fun createIntent(context: Context, input: Unit?) = Intent(
        Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
        Uri.parse("package:${context.packageName}")
    )

    override fun parseResult(resultCode: Int, intent: Intent?) =
        resultCode == Activity.RESULT_OK


}
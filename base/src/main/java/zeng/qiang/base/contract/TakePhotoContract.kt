package zeng.qiang.base.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract


/**
 * 自定义跳转Activity接收返回值Contract
 */
class TakePhotoContract(val activity: Activity) : ActivityResultContract<Unit?, Uri?>() {
    override fun createIntent(context: Context, input: Unit?) =
        Intent(context, Activity::class.java)


    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (resultCode == Activity.RESULT_OK) return intent?.data
        return null
    }
}
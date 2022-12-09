package zeng.qiang.base.utils

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import zeng.qiang.base.contract.UnknownSourceContract
import zeng.qiang.base.extension.toast
import java.io.File
import java.lang.ref.WeakReference

/**
 * App 安装工具类
 */

class InstallAppUtils(private val activity: ComponentActivity, private val apkUrl: String) {
    private var activityReference: WeakReference<ComponentActivity> = WeakReference(activity)
    private var launcher: ActivityResultLauncher<Unit?>? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 此方法需要在Activity Started 状态前调用
            launcher = activityReference.get()?.registerForActivityResult(UnknownSourceContract()) {
                if (it) {
                    install()
                } else {
                    activity.toast("未授予未知来源安装权限，无法安装")
                }
            }
        }


    }

    // 普通安装
    private fun install() {
        activityReference.get()?.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (packageManager.canRequestPackageInstalls()) {
                    installApk(this, apkUrl)
                } else {
                    startInstallPermissionSettingActivity()
                }
            } else {
                installApk(this, apkUrl)
            }
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun startInstallPermissionSettingActivity() {
        launcher?.launch(null)
    }

    private fun installApk(activity: ComponentActivity, apkUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        //从9.0开始，不能直接从非Activity环境（如Service，BroadcastReceiver）中启动Activity，需要加这个flag
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //表示对目标应用临时授权该Uri所代表的文件,如果添加此FLAG，将提示解析包失败
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val file = File(apkUrl)
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            FileProvider.getUriForFile(
                activity,
                activity.packageName + ".provider",
                file
            )
        else
            Uri.fromFile(file)

        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        activity.startActivity(intent)
    }
}
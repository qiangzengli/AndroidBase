package zeng.qiang.base.extension

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.LogUtils

/**
 * Activity Fragment扩展方法 toast
 */
fun LifecycleOwner.toast(msg: Any) {
    when (this) {
        is Activity -> {
            toast(this, msg.toString())
        }

        is Fragment -> {
            toast(requireContext(), msg.toString())
        }

        is Dialog -> {
            toast(context, msg.toString())
        }

        else -> {
            LogUtils.d("无法toast")
        }
    }
}

private fun toast(context: Context, msg: Any) {
    Toast.makeText(context, msg.toString(), Toast.LENGTH_SHORT).show()
}

package zeng.qiang.base.extension

import android.app.Activity
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ToastUtils

/**
 * Activity 扩展方法 toast
 */
fun Activity.toast(msg: Any) = ToastUtils.showShort(msg.toString())

/**
 * Fragment 扩展方法 toast
 */
fun Fragment.toast(msg: Any) = ToastUtils.showShort(msg.toString())
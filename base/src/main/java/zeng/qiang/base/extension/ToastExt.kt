package zeng.qiang.base.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.ToastUtils

/**
 * FragmentActivity 扩展方法 toast
 */
fun FragmentActivity.toast(msg: Any) = ToastUtils.showShort(msg.toString())

/**
 * Fragment 扩展方法 toast
 */
fun Fragment.toast(msg: Any) = ToastUtils.showShort(msg.toString())
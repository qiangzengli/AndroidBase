package zeng.qiang.base.extension

import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.ToastUtils

/**
 * Activity Fragment扩展方法 toast
 */
fun LifecycleOwner.toast(msg: Any) = ToastUtils.showShort(msg.toString())


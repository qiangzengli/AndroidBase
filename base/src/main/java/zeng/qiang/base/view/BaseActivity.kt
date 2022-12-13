package zeng.qiang.base.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import zeng.qiang.base.extension.toast
import zeng.qiang.base.utils.DensityUtils
import zeng.qiang.base.viewmodel.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * Activity 基类
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {
    lateinit var mContext: FragmentActivity
    lateinit var vm: VM
    private var vbTemp: VB? = null
    val vb get() = vbTemp!!

    private var loading: LoadingPopupView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 屏幕适配
        DensityUtils.setDensity(application, this)
        // 反射获取第一个泛型参数ViewModel类型
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz1 = type.actualTypeArguments[0] as Class<VM>
        // 生成对应的VeiwModel
        vm = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(clazz1)
        // 反射获取对应的ViewBinding类型
        val clazz2 = type.actualTypeArguments[1] as Class<VB>
        val method = clazz2.getMethod("inflate", LayoutInflater::class.java)
        vbTemp = method.invoke(null, layoutInflater) as VB
        mContext = this
        loading = XPopup.Builder(mContext)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .isRequestFocus(false)
            .hasStatusBar(false)
            .hasNavigationBar(false)
            .asLoading()
        setContentView(vb.root)
        initView(savedInstanceState)
        initData()
        // 监听页面loading弹窗状态
        vm.loadingState.observe(this) { isShow ->
            if (isShow) showLoading() else dismissLoading()
        }
        // 监听toast状态
        vm.toastState.observe(this) {
            toast(it)
        }
    }

    open fun initView(savedInstanceState: Bundle?) {}
    override fun onDestroy() {
        vbTemp = null
        super.onDestroy()
    }

    open fun initData() {}
    fun showLoading(msg: String = "加载中...") = loading?.setTitle(msg)?.show()
    fun dismissLoading() = loading?.dismiss()

}
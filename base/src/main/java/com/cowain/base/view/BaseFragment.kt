package com.cowain.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.cowain.base.extension.toast
import com.cowain.base.viewmodel.BaseViewModel

import java.lang.reflect.ParameterizedType

/**
 * fragment 基类
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment() {
    lateinit var mContext: FragmentActivity
    lateinit var vm: VM

    var vbTemp: VB? = null
    private val vb get() = vbTemp!!

    private var loading: LoadingPopupView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz1 = type.actualTypeArguments[0] as Class<VM>
        vm = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(clazz1)
        mContext = requireActivity()
        loading = XPopup.Builder(mContext)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .isRequestFocus(false)
            .hasStatusBar(false)
            .hasNavigationBar(false)
            .asLoading()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (vbTemp == null) {
            val type = javaClass.genericSuperclass as ParameterizedType
            val clazz2 = type.actualTypeArguments[1] as Class<VB>
            val method = clazz2.getMethod("inflate", LayoutInflater::class.java)
            vbTemp = method.invoke(null, layoutInflater) as VB

        }
        initView()
        initData()

        // 监听页面loading弹窗状态
        vm.loadingState.observe(this) { isShow ->
            if (isShow) showLoading() else dismissLoading()
        }
        // 监听toast状态
        vm.toastState.observe(this) {
            toast(it)
        }
        return vb.root


    }

    abstract fun initView()
    open fun initData() {}

    fun showLoading(msg: String = "加载中...") = loading?.setTitle(msg)?.show()
    fun dismissLoading() = loading?.dismiss()


    override fun onDestroyView() {
        super.onDestroyView()
        vbTemp = null
    }
}
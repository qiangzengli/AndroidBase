package zeng.qiang.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

import java.lang.reflect.ParameterizedType

/**
 * fragment 基类
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VM : ViewModel, VB : ViewBinding> : Fragment() {
    lateinit var mContext: FragmentActivity
    lateinit var vm: VM

    var vbTemp: VB? = null
    private val vb get() = vbTemp!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz1 = type.actualTypeArguments[0] as Class<VM>
        vm = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(clazz1)
        mContext = requireActivity()


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
        return vb.root


    }

    abstract fun initView()
    open fun initData() {}


    override fun onDestroyView() {
        super.onDestroyView()
        vbTemp = null
    }
}
package zeng.qiang.base

import android.app.Application
import com.blankj.utilcode.util.Utils

open class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}
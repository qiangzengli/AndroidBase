package zeng.qiang.base.utils

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import com.blankj.utilcode.util.ScreenUtils

/**
 * @Author lizengqiang
 * @Date 2019/11/16
 * @Time 16:12
 * @Description
 */
object DensityUtils {
    private const val WIDTH = 750f //参考设备的宽
    private const val HEIGHT = 1333f
    private var appDensity = 0f //表示屏幕宽度 = 0f
    private var appScaleDensity = 0f //字体缩放比例 默认等于appDensity = 0f

    fun setDensity(application: Application, activity: Activity) {
        val displayMetrics = application.resources.displayMetrics
        if (appDensity == 0f) {
            appDensity = displayMetrics.density
            appScaleDensity = displayMetrics.scaledDensity
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration) {
                    //字体发生改变   重新计算scaleDensity
                    if (newConfig.fontScale > 0) {
                        appScaleDensity =
                            application.resources.displayMetrics.scaledDensity
                    }
                }

                override fun onLowMemory() {

                }
            })
        }

        //计算目标density  scaledDensity
        // ScreenUtils.getScreenRotation(activity) 0,180 是竖屏 90和270是横屏
        val targetDensity = when (ScreenUtils.getScreenRotation(activity)) {
            0, 180 -> displayMetrics.widthPixels / WIDTH
            90, 270 -> displayMetrics.widthPixels / HEIGHT
            else -> displayMetrics.widthPixels / WIDTH
        }

        val targetScaleDensity =
            targetDensity * (appScaleDensity / appDensity)
        val targetDensityDpi = (targetDensity * 160).toInt()
        val dm = activity.resources.displayMetrics
        dm.density = targetDensity
        dm.scaledDensity = targetScaleDensity
        dm.densityDpi = targetDensityDpi
    }
}
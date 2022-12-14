package com.cowain.base

import android.app.Application
import com.blankj.utilcode.util.Utils

open class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}
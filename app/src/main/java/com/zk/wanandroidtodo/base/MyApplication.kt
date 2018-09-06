package com.zk.wanandroidtodo.base

import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatDelegate
import com.zk.wanandroidtodo.utils.Constant
import com.zk.wanandroidtodo.utils.Preference

/**
 * @description:     app初始化
 * @author:         zhukai
 * @date:     2018/8/22 13:57
 */
class MyApplication : Application() {

    companion object {
        lateinit var mContext: Context
        lateinit var mApplication: MyApplication
    }

    /**
     * 是否为夜间模式
     */
    private var isNight: Boolean by Preference(Constant.NIGHT_MODEL, false)

    override fun onCreate() {
        super.onCreate()
        mContext = this
        mApplication = this
        initNightMode() // 初始化夜间模式
    }

    fun initNightMode() {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
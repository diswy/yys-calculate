package com.yys.king_of_europe.fu

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.yys.king_of_europe.fu.di.DaggerAppComponent
import xiaofu.lib.base.BuildConfig
import javax.inject.Inject

/**
 *
 * Created by @author xiaofu on 2019/5/16.
 */
class YysApp : Application() {
    companion object {
        lateinit var instance: YysApp
            private set
    }

    /**
     * 用于生产ViewModel，注入单例成员
     * 例如：OKHttpClient、Retrofit、以及保证内容库是单例的
     */
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate() {
        super.onCreate()
        instance = this

        DaggerAppComponent.builder().application(this)
                .build().inject(this)

        initLogger()
    }


    /**
     * Logger初始化
     */
    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("yys")
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.SHOW_LOG
            }
        })
    }
}
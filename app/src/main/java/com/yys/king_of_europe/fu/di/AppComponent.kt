package com.yys.king_of_europe.fu.di

import android.app.Application
import com.yys.king_of_europe.fu.YysApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 *
 * Created by @author xiaofu on 2019/5/16.
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: YysApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
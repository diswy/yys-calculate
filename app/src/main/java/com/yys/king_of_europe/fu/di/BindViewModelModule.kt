package com.yys.king_of_europe.fu.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yys.king_of_europe.fu.viewmodel.YuHunViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import xiaofu.lib.network.di.ViewModelFactory
import xiaofu.lib.network.di.ViewModelKey

/**
 *
 * Created by @author xiaofu on 2019/5/16.
 */
@Module
abstract class BindViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(YuHunViewModel::class)
    abstract fun bindYuHunViewModel(yuHunViewModel: YuHunViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
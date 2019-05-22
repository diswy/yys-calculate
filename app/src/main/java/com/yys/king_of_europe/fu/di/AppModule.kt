package com.yys.king_of_europe.fu.di

import androidx.room.Room
import com.yys.king_of_europe.fu.YysApp
import com.yys.king_of_europe.fu.db.YuHunDao
import com.yys.king_of_europe.fu.db.YuHunDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *
 * Created by @author xiaofu on 2019/5/16.
 */
@Module(includes = [BindViewModelModule::class])
class AppModule {

    @Provides
    fun provideApplication(): YysApp {
        return YysApp.instance
    }

    @Singleton
    @Provides
    fun provideDb(app: YysApp): YuHunDb {
        return Room.databaseBuilder(app, YuHunDb::class.java, "yuhun.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideYuHunDao(db: YuHunDb): YuHunDao {
        return db.yuhunDao()
    }

}
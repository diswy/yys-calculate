package com.yys.king_of_europe.fu.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yys.king_of_europe.fu.vo.YuHunSource

/**
 *
 * Created by @author xiaofu on 2019/5/21.
 */
@Database(
    entities = [YuHunSource::class],
    version = 1,
    exportSchema = false
)
abstract class YuHunDb : RoomDatabase() {
    abstract fun yuhunDao(): YuHunDao
}
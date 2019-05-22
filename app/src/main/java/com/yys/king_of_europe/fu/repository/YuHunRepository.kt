package com.yys.king_of_europe.fu.repository

import android.util.Log
import com.yys.king_of_europe.fu.YysApp
import com.yys.king_of_europe.fu.db.YuHunDao
import com.yys.king_of_europe.fu.vo.YuHunResult
import com.yys.king_of_europe.fu.vo.YuHunSource
import xiaofu.lib.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * Created by @author xiaofu on 2019/5/16.
 */
@Singleton
class YuHunRepository @Inject constructor(
        private val app: YysApp,
        private val executor: AppExecutors,
        private val dao: YuHunDao
) {

    fun writeToDb(list: List<YuHunSource>) {
        executor.diskIO().execute {
            dao.insertYuHunAll(list)
            Log.w("yys", "数据库写入成功")
        }
    }

    fun saveYuHunResult(data: YuHunResult) {
        executor.diskIO().execute {
            dao.insertYuHunResult(data)
            Log.w("yys", "御魂计算结果写入成功")
        }
    }

    fun deleteYuHunResultAll() {
        executor.diskIO().execute {
            dao.deleteYuHunResultAll()
            Log.w("yys", "御魂计算结果已全部删除")
        }
    }


    fun queryYuHunByPos(pos: Int) = dao.queryYuHunByPos(pos)

    fun queryYuHunAll() = dao.queryYuHunAll()
}
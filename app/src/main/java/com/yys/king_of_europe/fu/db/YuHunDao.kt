package com.yys.king_of_europe.fu.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yys.king_of_europe.fu.vo.YuHunResult
import com.yys.king_of_europe.fu.vo.YuHunSource

/**
 *
 * Created by @author xiaofu on 2019/5/21.
 */
@Dao
interface YuHunDao {

    @Query("DELETE FROM YuHunSource")
    fun deleteYuHunAll()

    @Query("DELETE FROM YuHunResult")
    fun deleteYuHunResultAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertYuHunAll(listAll: List<YuHunSource>)

    @Query("SELECT * FROM YuHunSource WHERE pos =:pos")
    fun queryYuHunByPos(pos: Int): LiveData<List<YuHunSource>>

    @Query("SELECT * FROM YuHunSource")
    fun queryYuHunAll(): LiveData<List<YuHunSource>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertYuHunResult(listAll: List<YuHunResult>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertYuHunResult(result: YuHunResult)

}
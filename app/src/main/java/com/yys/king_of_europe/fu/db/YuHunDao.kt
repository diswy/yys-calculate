package com.yys.king_of_europe.fu.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.yys.king_of_europe.fu.vo.YuHunSource

/**
 *
 * Created by @author xiaofu on 2019/5/21.
 */
@Dao
interface YuHunDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertYuHunAll(listAll: List<YuHunSource>)

}
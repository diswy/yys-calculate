package com.yys.king_of_europe.fu.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.yys.king_of_europe.fu.repository.YuHunRepository
import com.yys.king_of_europe.fu.vo.YuHunLoadStatus
import com.yys.king_of_europe.fu.vo.YuHunResult
import com.yys.king_of_europe.fu.vo.YuHunSource
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import xiaofu.lib.utils.fromJsonArray
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import javax.inject.Inject

/**
 *
 * Created by @author xiaofu on 2019/5/16.
 */
class YuHunViewModel @Inject constructor(
        private val repository: YuHunRepository
) : ViewModel() {

    val yuhunList: MutableLiveData<List<YuHunSource>> = MutableLiveData()

    fun loadYuHun(path: String): Boolean {
        val yFile = File(path)
        if (!yFile.exists())
            return false

        val strBuilder = StringBuilder()
        val inputStream = FileInputStream(path)
        val bf = BufferedReader(InputStreamReader(inputStream))
        while (bf.readLine().apply {
                    if (this != null) {
                        strBuilder.append(this)
                    }
                } != null) {
        }

        val filterData = strBuilder.toString().replace("\"yuhun_ocr2.0\",", "")
        try {
            val list = Gson().fromJsonArray(filterData, YuHunSource::class.java)
            list as ArrayList
            yuhunList.value = list
            // 写入数据库
            repository.writeToDb(list)
        } catch (e: Exception) {
            Log.e("yys", e.message)
            return false
        }
        return true
    }

    /**
     * 计算御魂可以生成多少种结果
     */
    fun calculateYuHun(data: YuHunLoadStatus) {
        repository.deleteYuHunResultAll()

        val disposable = Flowable.just(data)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .subscribe {
                    calculate(data)
                }

    }

    private fun calculate(data: YuHunLoadStatus) {
//        val list = ArrayList<YuHunSource>()
        var count: Long = 0
        // 计算所有结果，会有6层结果嵌套
        data.list1.forEach { yuhun1 ->

            data.list2.forEach { yuhun2 ->

                data.list3.forEach { yuhun3 ->

                    data.list4.forEach { yuhun4 ->

                        data.list5.forEach { yuhun5 ->

                            data.list6.forEach { yuhun6 ->
                                count++
                                Log.e("yys", "御魂结果计算ing...当前计算到第${count}条")
                            }

                        }

                    }

                }

            }

        }

        Log.e("yys", "御魂结果计算完毕，总共有${count}可能性")
    }

    private fun totalYuHun(yuhunList: ArrayList<YuHunSource>) {
        val result = YuHunResult()
        var gongji = 0.0
        var gongjijiacheng = 0.0
        var fangyu = 0.0
        var fangyujiacheng = 0.0
        var shengming = 0.0
        var shengmingjiacheng = 0.0
        var sudu = 0.0
        var mingzhong = 0.0
        var dikang = 0.0
        var baoji = 0.0
        var baojishanghai = 0.0
        yuhunList.forEach { yuhun ->
            gongji += yuhun.gongji
            gongjijiacheng += yuhun.gongjijiacheng
            fangyu += yuhun.fangyu
            fangyujiacheng += yuhun.fangyujiacheng
            shengming += yuhun.shengming
            shengmingjiacheng += yuhun.shengmingjiacheng
            sudu += yuhun.sudu
            mingzhong += yuhun.mingzhong
            dikang += yuhun.dikang
            baoji += yuhun.baoji
            baojishanghai += yuhun.baojishanghai
        }

        result.gongji = gongji
        result.gongjijiacheng = gongjijiacheng
        result.fangyu = fangyu
        result.fangyujiacheng = fangyujiacheng
        result.shengming = shengming
        result.shengmingjiacheng = shengmingjiacheng
        result.sudu = sudu
        result.mingzhong = mingzhong
        result.dikang = dikang
        result.baoji = baoji
        result.baojishanghai = baojishanghai

        repository.saveYuHunResult(result)
    }

    /**
     * 查找御魂
     */
    fun queryYuHunByPos(pos: Int) = repository.queryYuHunByPos(pos)

    fun queryYuHunAll() = repository.queryYuHunAll()

}
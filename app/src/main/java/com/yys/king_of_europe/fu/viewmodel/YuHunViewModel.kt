package com.yys.king_of_europe.fu.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.yys.king_of_europe.fu.repository.YuHunRepository
import com.yys.king_of_europe.fu.vo.YuHunSource
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
    private fun calculateYuHun() {

    }

}
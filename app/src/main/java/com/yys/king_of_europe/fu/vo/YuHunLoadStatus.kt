package com.yys.king_of_europe.fu.vo

/**
 *
 * Created by @author xiaofu on 2019/5/22.
 */
data class YuHunLoadStatus(
        var pos1: Boolean = false,
        var pos2: Boolean = false,
        var pos3: Boolean = false,
        var pos4: Boolean = false,
        var pos5: Boolean = false,
        var pos6: Boolean = false,
        val list1: ArrayList<YuHunSource> = ArrayList(),
        val list2: ArrayList<YuHunSource> = ArrayList(),
        val list3: ArrayList<YuHunSource> = ArrayList(),
        val list4: ArrayList<YuHunSource> = ArrayList(),
        val list5: ArrayList<YuHunSource> = ArrayList(),
        val list6: ArrayList<YuHunSource> = ArrayList()
) {

    fun allLoadEnd(): Boolean {
        return (pos1 && pos2 && pos3 && pos4 && pos5 && pos6)
    }
}

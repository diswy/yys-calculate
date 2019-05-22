package xiaofu.lib.view.drawable

import android.content.res.ColorStateList

/**
 * 处理字体颜色序列的工具
 * Created by @author xiaofu on 2018/12/15.
 */
class ColorStateListTool {

    /**
     * 生成一个简单用于按压改变颜色的序列
     */
    fun createSimple(pressed: Int, normal: Int): ColorStateList {
        val colors = intArrayOf(pressed, normal)
        val states = arrayOfNulls<IntArray>(2)

        states[0] = intArrayOf(android.R.attr.state_pressed)
        states[1] = intArrayOf()

        return ColorStateList(states, colors)
    }
}
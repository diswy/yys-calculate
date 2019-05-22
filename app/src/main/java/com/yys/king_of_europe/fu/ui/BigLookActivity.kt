package com.yys.king_of_europe.fu.ui

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yys.king_of_europe.fu.R
import com.yys.king_of_europe.fu.YysApp
import com.yys.king_of_europe.fu.adapter.YuHunLookAdapter
import com.yys.king_of_europe.fu.viewmodel.YuHunViewModel
import com.yys.king_of_europe.fu.vo.YuHunSource
import kotlinx.android.synthetic.main.activity_big_look.*
import xiaofu.lib.base.activity.BaseActivity
import java.lang.StringBuilder

class BigLookActivity : BaseActivity() {

    private lateinit var yysModel: YuHunViewModel
    private val adapter = YuHunLookAdapter()
    private val yuhunList: ArrayList<YuHunSource> = ArrayList()

    override fun getLayoutRes(): Int = R.layout.activity_big_look

    override fun initialize() {
        yysModel = ViewModelProviders.of(this, YysApp.instance.factory).get(YuHunViewModel::class.java)

        yysModel.queryYuHunAll().observe(this, Observer {
            yuhunList.clear()
            yuhunList.addAll(it)
            adapter.setNewData(it)
        })

        yRv.layoutManager = GridLayoutManager(this, 4)
        yRv.adapter = adapter
    }

    override fun bindListener() {
        posRg.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.pos1 -> updateYuHun(1)
                R.id.pos2 -> updateYuHun(2)
                R.id.pos3 -> updateYuHun(3)
                R.id.pos4 -> updateYuHun(4)
                R.id.pos5 -> updateYuHun(5)
                R.id.pos6 -> updateYuHun(6)
            }
        }

        adapter.setOnItemClickListener { _, view, position ->
            val data = adapter.getItem(position) ?: return@setOnItemClickListener

            val builder = StringBuilder()

            if (data.gongji > 0) {
                builder.append("攻击：${data.gongji}\n")
            }
            if (data.gongjijiacheng > 0) {
                builder.append("攻击加成：${data.gongjijiacheng}\n")
            }
            if (data.fangyu > 0) {
                builder.append("防御：${data.fangyu}\n")
            }
            if (data.fangyujiacheng > 0) {
                builder.append("防御加成：${data.fangyujiacheng}\n")
            }
            if (data.shengming > 0) {
                builder.append("生命：${data.shengming}\n")
            }
            if (data.shengmingjiacheng > 0) {
                builder.append("生命加成：${data.shengmingjiacheng}\n")
            }
            if (data.sudu > 0) {
                builder.append("速度：${data.sudu}\n")
            }
            if (data.mingzhong > 0) {
                builder.append("效果命中：${data.mingzhong}\n")
            }
            if (data.dikang > 0) {
                builder.append("效果抵抗：${data.dikang}\n")
            }
            if (data.baoji > 0) {
                builder.append("暴击：${data.baoji}\n")
            }
            if (data.baojishanghai > 0) {
                builder.append("暴击伤害：${data.baojishanghai}\n")
            }

            tvCurrent.text = builder.toString()

        }
    }

    private fun updateYuHun(pos: Int) {
        val newList = yuhunList.filter {
            it.pos == pos
        }
        adapter.setNewData(newList)
    }

}

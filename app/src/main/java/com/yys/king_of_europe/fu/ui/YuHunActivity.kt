package com.yys.king_of_europe.fu.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yys.king_of_europe.fu.R
import com.yys.king_of_europe.fu.YysApp
import com.yys.king_of_europe.fu.viewmodel.YuHunViewModel
import com.yys.king_of_europe.fu.vo.YuHunLoadStatus
import kotlinx.android.synthetic.main.activity_yu_hun.*
import org.jetbrains.anko.toast
import xiaofu.lib.base.activity.BaseActivity

class YuHunActivity : BaseActivity() {

    private lateinit var yysModel: YuHunViewModel

    private val loadStatus = YuHunLoadStatus()

    override fun getLayoutRes(): Int = R.layout.activity_yu_hun

    override fun initialize() {
        yysModel = ViewModelProviders.of(this, YysApp.instance.factory).get(YuHunViewModel::class.java)

        yysModel.queryYuHunByPos(1).observe(this, Observer {
            tv_1_count.text = it.size.toString()
            loadStatus.pos1 = true
            loadStatus.list1.clear()
            loadStatus.list1.addAll(it)
        })
        yysModel.queryYuHunByPos(2).observe(this, Observer {
            tv_2_count.text = it.size.toString()
            loadStatus.pos2 = true
            loadStatus.list2.clear()
            loadStatus.list2.addAll(it)
        })
        yysModel.queryYuHunByPos(3).observe(this, Observer {
            tv_3_count.text = it.size.toString()
            loadStatus.pos3 = true
            loadStatus.list3.clear()
            loadStatus.list3.addAll(it)
        })
        yysModel.queryYuHunByPos(4).observe(this, Observer {
            tv_4_count.text = it.size.toString()
            loadStatus.pos4 = true
            loadStatus.list4.clear()
            loadStatus.list4.addAll(it)
        })
        yysModel.queryYuHunByPos(5).observe(this, Observer {
            tv_5_count.text = it.size.toString()
            loadStatus.pos5 = true
            loadStatus.list5.clear()
            loadStatus.list5.addAll(it)
        })
        yysModel.queryYuHunByPos(6).observe(this, Observer {
            tv_6_count.text = it.size.toString()
            loadStatus.pos6 = true
            loadStatus.list6.clear()
            loadStatus.list6.addAll(it)
        })
    }

    override fun bindListener() {
        btn_calculate_result.setOnClickListener {
            if (loadStatus.allLoadEnd()) {
                yysModel.calculateYuHun(loadStatus)
            } else {
                toast("御魂没有加载完毕，不能计算！")
            }
        }
    }

}

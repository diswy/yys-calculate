package com.yys.king_of_europe.fu.adapter

import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yys.king_of_europe.fu.R
import com.yys.king_of_europe.fu.vo.YuHunSource
import de.hdodenhof.circleimageview.CircleImageView
import xiaofu.lib.inline.loadUrl

/**
 *
 * Created by @author xiaofu on 2019/5/22.
 */
class YuHunLookAdapter : BaseQuickAdapter<YuHunSource, BaseViewHolder>(R.layout.item_yuhun_look) {

    override fun convert(helper: BaseViewHolder?, item: YuHunSource?) {
        helper ?: return
        item ?: return

        helper.setText(R.id.tv, item.type)

        val icon: CircleImageView = helper.getView(R.id.ic_yuhun)
        when (item.type) {
            "蚌精" -> icon.loadUrl(mContext, R.drawable.y_bangjing)
            "被服" -> icon.loadUrl(mContext, R.drawable.y_beifu)
            "地藏像" -> icon.loadUrl(mContext, R.drawable.y_dizangxiang)
            "地震鲶" -> icon.loadUrl(mContext, R.drawable.y_dizhennian)
            "反枕" -> icon.loadUrl(mContext, R.drawable.y_fanzhen)
            "返魂香" -> icon.loadUrl(mContext, R.drawable.y_fanhunxiang)
            "蝠翼" -> icon.loadUrl(mContext, R.drawable.y_fuyi)
            "荒骷髅" -> icon.loadUrl(mContext, R.drawable.y_huangkulou)
            "火灵" -> icon.loadUrl(mContext, R.drawable.y_huoling)
            "镜姬" -> icon.loadUrl(mContext, R.drawable.y_jingji)
            "狂骨" -> icon.loadUrl(mContext, R.drawable.y_kuanggu)
            "胧车" -> icon.loadUrl(mContext, R.drawable.y_longche)
            "轮入道" -> icon.loadUrl(mContext, R.drawable.y_lunrudao)
            "魅妖" -> icon.loadUrl(mContext, R.drawable.y_meiyao)
            "鸣屋" -> icon.loadUrl(mContext, R.drawable.y_mingwu)
            "木魅" -> icon.loadUrl(mContext, R.drawable.y_mumei)
            "涅槃之火" -> icon.loadUrl(mContext, R.drawable.y_niepanzhihuo)
            "破势" -> icon.loadUrl(mContext, R.drawable.y_poshi)
            "日女巳时" -> icon.loadUrl(mContext, R.drawable.y_rinv)
            "三味" -> icon.loadUrl(mContext, R.drawable.y_sanwei)
            "伤魂鸟" -> icon.loadUrl(mContext, R.drawable.y_shanghunniao)
            "蜃气楼" -> icon.loadUrl(mContext, R.drawable.y_shenqilou)
            "树妖" -> icon.loadUrl(mContext, R.drawable.y_shuyao)
            "骰子鬼" -> icon.loadUrl(mContext, R.drawable.y_touzigui)
            "土蜘蛛" -> icon.loadUrl(mContext, R.drawable.y_tuzhizhu)
            "网切" -> icon.loadUrl(mContext, R.drawable.y_wangqie)
            "魍魉之匣" -> icon.loadUrl(mContext, R.drawable.y_wangliao)
            "心眼" -> icon.loadUrl(mContext, R.drawable.y_xinyan)
            "雪幽魂" -> icon.loadUrl(mContext, R.drawable.y_xueyouhun)
            "阴摩罗" -> icon.loadUrl(mContext, R.drawable.y_yinmoluo)
            "幽谷响" -> icon.loadUrl(mContext, R.drawable.y_youguxiang)
            "招财猫" -> icon.loadUrl(mContext, R.drawable.y_zhaocai)
            "针女" -> icon.loadUrl(mContext, R.drawable.y_zhennv)
            "珍珠" -> icon.loadUrl(mContext, R.drawable.y_zhenzhu)
            "镇墓兽" -> icon.loadUrl(mContext, R.drawable.y_zhenmushou)
            "狰" -> icon.loadUrl(mContext, R.drawable.y_zheng)
            "薙魂" -> icon.loadUrl(mContext, R.drawable.y_zhihun)
            "钟灵" -> icon.loadUrl(mContext, R.drawable.y_zhongling)
            else ->Log.w("yys","缺失字段：${item.type}")
        }
    }


}
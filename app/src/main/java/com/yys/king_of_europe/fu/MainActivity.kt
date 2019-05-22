package com.yys.king_of_europe.fu

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yys.king_of_europe.fu.utils.FileHelper
import com.yys.king_of_europe.fu.viewmodel.YuHunViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import xiaofu.lib.base.activity.BaseActivity

class MainActivity : BaseActivity() {

    private val fileBackCode = 1021// 文件请求码

    private lateinit var yysModel: YuHunViewModel

    override fun getLayoutRes(): Int = R.layout.activity_main

    @SuppressLint("SetTextI18n")
    override fun initialize() {
        yysModel = ViewModelProviders.of(this, YysApp.instance.factory).get(YuHunViewModel::class.java)

        yysModel.yuhunList.observe(this, Observer {
            tv_info.text = "御魂加载成功，当前+15御魂数量：${it.size}"
        })

    }

    override fun bindListener() {
        btn_import_yuhun.setOnClickListener {
            doImportYuHun()
        }

        btn_calculate_yuhun.setOnClickListener {

        }

    }

    private fun doImportYuHun() {
        val permissions = RxPermissions(this)
        val mDisposable = permissions.request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
                .subscribe({
                    if (it) {
                        openFile()
                    } else {
                        toast("没有权限，无法读取御魂，白玩~")
                    }
                }, {
                    Log.e("yys", "错误：${it.message}")
                })
        mDisposablePool.add(mDisposable)
    }

    private fun openFile() {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.type = "*/*"
        i.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(i, fileBackCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == fileBackCode) {
            try {
                val uri = data?.data
                val path = FileHelper.getPath(this, uri)

                if (path.endsWith(".json")) {
                    Log.w("yys", "文件路径：$path")
                    if (yysModel.loadYuHun(path)) {
                        toast("御魂加载成功")
                    } else {
                        toast("御魂加载失败，可能该文件不是御魂数据")
                    }
                } else {
                    toast("请诸位大佬，观众老爷擦亮双眼，选中正确的御魂文件.")
                }

            } catch (e: Exception) {
                toast(e.message ?: "未知错误")
            }
        }
    }


}

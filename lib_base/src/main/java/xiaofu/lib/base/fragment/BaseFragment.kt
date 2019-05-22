package xiaofu.lib.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.JsonParseException
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.toast
import org.json.JSONException
import xiaofu.lib.base.R
import xiaofu.lib.base.databinding.DialogSimpleLoadingBinding
import xiaofu.lib.base.timer.ITimer
import xiaofu.lib.view.dialog.FancyDialogFragment
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import java.util.concurrent.TimeUnit

/**
 *
 * Created by @author xiaofu on 2018/12/7.
 */
abstract class BaseFragment : Fragment(), AnkoLogger {
    private lateinit var rootView: View

    /**
     * 获取视图ID
     */
    abstract fun getLayoutRes(): Int

    /**
     * 带Tag标签的日志
     * note:真机调试中，info以下级别的日志容易被过滤不显示
     */
    protected val log = AnkoLogger("xiaofu")

    protected val mDisposablePool: ArrayList<Disposable> = ArrayList()// 维护一个网络请求相关的池
    protected var mDisposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!::rootView.isInitialized) {
            rootView = inflater.inflate(getLayoutRes(), container, false)
        } else {
            val parent = rootView.parent as ViewGroup
            parent.removeView(rootView)
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            initialize(it)
            bindListener(it)
            requestNetwork()
        } ?: throw RuntimeException("Invalid Activity")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mDisposable?.dispose()
        mDisposablePool.forEach {
            it.dispose()
        }
    }

    abstract fun initialize(activity: FragmentActivity)

    protected open fun bindListener(activity: FragmentActivity) {}

    protected open fun requestNetwork() {}

    /**
     * 强制显示软键盘
     * @param view 需要是EditText及其子类
     */
    protected open fun showSoftKeyboard(context: Context, view: View) {
        if (view.requestFocus()) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    /**
     * 强制关闭软键盘
     * @param view 这里可以是任意view
     */
    protected open fun hideSoftKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 计时任务,如果只关心过程或结果可使用子类接口
     * @param second 秒
     * @param timer 回调
     */
    protected open fun timer(second: Long, timer: ITimer) {
        mDisposable?.dispose()
        mDisposable = Flowable.intervalRange(0, second, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    timer.onTime((second - it - 1).toInt())
                }
                .doOnComplete {
                    timer.onTimeEnd()
                }
                .doOnError {
                }
                .subscribe()
    }

    protected open fun handleExceptions(t: Throwable?) {
        if (t == null) {
            activity?.toast("未知错误")
            return
        }
        val errorMessage = when (t) {
            is SocketException -> "网络异常，请检查网络重试"
            is SocketTimeoutException -> "请求超时,请重新尝试"
            is UnknownHostException -> "您似乎断开了与外网的连接，请检查外网是否畅通后重试"
            is JsonParseException -> "数据解析失败，请联系管理员"
            is JSONException -> "数据解析失败，请联系管理员"
            is ParseException -> "数据解析失败，请联系管理员"
            else -> t.message ?: "未知错误"
        }
        log.error { t.message }
        activity?.toast(errorMessage)
    }

    //--------路由封装--------
    protected open fun navigation(path: String) {
        ARouter.getInstance()
                .build(path)
                .navigation()
    }

    protected open fun navigation(path: String, vararg params: Pair<String, Any?>) {
        val aRouter = ARouter.getInstance()
                .build(path)
        params.forEach {
            when {
                it.second is Int -> aRouter.withInt(it.first, it.second as Int)
                it.second is String -> aRouter.withString(it.first, it.second as String)
                else -> aRouter.withObject(it.first, it.second)
            }
        }
        aRouter.navigation()
    }

    protected open fun navigation(path: String, options: ActivityOptionsCompat) {
        ARouter.getInstance()
                .build(path)
                .withOptionsCompat(options)
                .navigation()
    }

    protected open fun navigationAsFrag(path: String): Fragment {
        return ARouter.getInstance()
                .build(path)
                .navigation() as Fragment
    }

    protected open fun navigationAsFrag(path: String, vararg params: Pair<String, Any?>): Fragment {
        val aRouter = ARouter.getInstance()
                .build(path)
        params.forEach {
            when {
                it.second is Int -> aRouter.withInt(it.first, it.second as Int)
                it.second is String -> aRouter.withString(it.first, it.second as String)
                else -> aRouter.withObject(it.first, it.second)
            }
        }
        return aRouter.navigation() as Fragment
    }
    //--------路由封装--------


    //--------简单Loading-------
    protected open fun showLoading(msg: String): FancyDialogFragment {
        val loading = FancyDialogFragment.create()
                .setCanCancelOutside(false)
                .setLayoutRes(R.layout.dialog_simple_loading)
                .setWidth(requireContext(), 260)
                .setViewListener { _, viewDataBinding ->
                    viewDataBinding as DialogSimpleLoadingBinding
                    viewDataBinding.tvMsg.text = msg
                }
        loading.show(fragmentManager, "loading...")
        return loading
    }

    protected open fun dismissLoading(dialog: FancyDialogFragment) {
        dialog.dismiss()
    }
}
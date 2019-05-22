package xiaofu.lib.base.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
import xiaofu.lib.base.timer.ITimer
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import java.util.concurrent.TimeUnit

/**
 * 封装基础层，避免DataBinding过渡滥用
 * 封装部分通用方法
 *
 * 日后如果使用kotlin协程，注意实现CoroutineScope接口
 * Created by @author xiaofu on 2018/12/3.
 */
abstract class BaseActivity : AppCompatActivity(), AnkoLogger {

    protected lateinit var TAG: String
    protected lateinit var root: ViewGroup// 根视图容器
    /**
     * 带Tag标签的日志
     * note:真机调试中，info以下级别的日志容易被过滤不显示
     */
    protected val log = AnkoLogger("xiaofu")

    protected val mDisposablePool: ArrayList<Disposable> = ArrayList()// 维护一个网络请求相关的池
    private var mDisposable: Disposable? = null// 倒计时专用

    // kotlin协程，当前版本无法兼容阿里热更新，注释掉协程保留写法
//    protected lateinit var job: Job
//    override val coroutineContext: CoroutineContext
//        get() = job + Dispatchers.Main

    /**
     * 获取视图ID
     */
    abstract fun getLayoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        job = Job()
        TAG = componentName.className
        root = window.decorView.findViewById(android.R.id.content)
        if (isEmptyBackground()) {
            window.decorView.background = null
        }
        /**
         * 不同于style中的半透明状态栏，此设置为全屏沉浸式
         * 后一种为透明状态栏样式
         */
        if (isFullScreen()) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            if (isStatusDarkMode()) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        } else if (isTranslucentMode()) {
            val decorView = window.decorView
            val option = if (isStatusDarkMode()) View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }
        if (isKeepScreenOn()) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        /**
         * 状态栏颜色修改
         */
        if (setStatusBarColor() != null) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, setStatusBarColor()!!)
        }
        setView()
        initialize()
        bindListener()
        requestNetwork()
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposablePool.forEach {
            it.dispose()
        }
        mDisposable?.dispose()
//        job.cancel()
    }

    protected open fun isFullScreen() = false

    protected open fun isKeepScreenOn() = false

    protected open fun isTranslucentMode() = false

    protected open fun isStatusDarkMode() = false

    protected open fun isEmptyBackground() = false

    protected open fun setStatusBarColor(): Int? = null

    protected open fun setView() {
        setContentView(getLayoutRes())
    }

    abstract fun initialize()

    protected open fun bindListener() {}

    protected open fun requestNetwork() {}

    /**
     * 强制显示软键盘
     * @param view 需要是EditText及其子类
     */
    protected open fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    /**
     * 强制关闭软键盘
     * @param view 这里可以是任意view
     */
    protected open fun hideSoftKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 计时任务,如果只关心过程或结果可使用子类接口
     * @param second 秒
     * @param timer 回调
     */
    protected open fun timer(second: Long, timer: ITimer) {
//        launch {// 协程写法的倒计时
//            for (i in second downTo 1) {
//                timer.onTime(i)
//                delay(1000L)
//            }
//            timer.onTimeEnd()
//        }
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
            toast("未知错误")
            return
        }
        val errorMessage = when (t) {
            is SocketException -> "网络异常，请检查网络重试"
            is SocketTimeoutException -> "请求超时,请重新尝试"
            is UnknownHostException -> "您似乎断开了与外网的连接，请检查外网是否畅通后重试"
            is JsonParseException -> "数据解析失败，请联系管理员"
            is JSONException -> "数据解析失败，请联系管理员"
            is ParseException -> "数据解析失败，请联系管理员"
            else -> t.message ?: "未知错误,请完全退出应用稍后重试，如果仍有问题，请联系管理员"
        }
        log.error { t.message }
        toast(errorMessage)
    }

    /**
     * 阿里路由传值失败，可能会是机型引发。如遇此错误，只好联系我们
     */
    protected open fun myError() {
        toast("发生了预期之外的错误，请联系云课堂管理员")
        finish()
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
}
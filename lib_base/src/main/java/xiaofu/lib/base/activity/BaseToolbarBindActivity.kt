package xiaofu.lib.base.activity

import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import xiaofu.lib.base.R

/**
 *
 * Created by @author xiaofu on 2018/11/30.
 */
abstract class BaseToolbarBindActivity<T : ViewDataBinding> : BaseBindActivity<T>() {
    private lateinit var mTitle: TextView
    private lateinit var mToolbar: Toolbar

    abstract fun setToolbarTitle(): String

    override fun setView() {
        setContentView(R.layout.activity_base_toolbar_bind)
        val parent = findViewById<FrameLayout>(R.id.container)
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), getLayoutRes(), parent, true)

        mToolbar = findViewById(R.id.toolbar)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        mTitle = findViewById(R.id.title)

        mTitle.text = setToolbarTitle()
    }

    protected open fun userSetTitle(title: String) {
        mTitle.text = title
    }

    protected open fun userSetToolbarBackground(@ColorRes color: Int) {
        mToolbar.setBackgroundColor(ContextCompat.getColor(this, color))
    }

}
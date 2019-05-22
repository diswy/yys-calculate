package xiaofu.lib.base.activity

import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import xiaofu.lib.base.R

/**
 *
 * Created by @author xiaofu on 2018/12/3.
 */
abstract class BaseToolbarActivity : BaseActivity() {
    private lateinit var mTitle: TextView
    private lateinit var mToolbar: Toolbar

    override fun setView() {
        setContentView(R.layout.activity_base_toolbar_bind)
        val parent = findViewById<FrameLayout>(R.id.container)
        LayoutInflater.from(this).inflate(getLayoutRes(), parent, true)

        mToolbar = findViewById(R.id.toolbar)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        mTitle = findViewById(R.id.title)
    }

    protected open fun setToolbarTitle(str: String) {
        mTitle.text = str
    }
}
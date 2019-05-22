package xiaofu.lib.base.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * DataBinding基础Activity
 * Created by @author xiaofu on 2018/11/27.
 */
abstract class BaseBindActivity<T : ViewDataBinding> : BaseActivity() {

    /**
     * 对应视图返回的ViewDataBinding
     */
    protected lateinit var binding: T

    override fun setView() {
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
    }

    override fun initialize() {
        initialize(binding)
    }

    override fun bindListener() {
        bindListener(binding)
    }

    abstract fun initialize(binding: T)

    protected open fun bindListener(binding: T) {

    }

}
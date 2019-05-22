package xiaofu.lib.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity

/**
 *
 * Created by @author xiaofu on 2018/12/7.
 */
abstract class BaseBindFragment<T : ViewDataBinding> : BaseFragment() {
    /**
     * 对应视图返回的ViewDataBinding
     */
    protected lateinit var binding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return binding.root
    }

    override fun initialize(activity: FragmentActivity) {
        initialize(activity, binding)
    }

    abstract fun initialize(activity: FragmentActivity, binding: T)

    override fun bindListener(activity: FragmentActivity) {
        bindListener(activity, binding)
    }

    protected open fun bindListener(activity: FragmentActivity, binding: T) {

    }

}
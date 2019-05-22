package xiaofu.lib.view.dialog

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import org.jetbrains.anko.dip

/**
 * 友好性弹框
 * 大人者，言不必信，行不必果，惟义所在
 * Created by @author xiaofu on 2019/4/8.
 */
class FancyDialogFragment : DialogFragment() {
    private val DEFAULT_DIM = 0.4f// 遮罩

    companion object {
        fun create() = FancyDialogFragment()
    }

    private var mLayoutRes: Int = 0// 视图
    private var canCancelOutside = true// 点击外部消失，默认为true
    private var isBottomMode = false// 底部对话框模式，默认为false,该模式设置宽高会无效
    private var width = -1
    private var height = -2
    private var mListener: ((FancyDialogFragment, ViewDataBinding) -> Unit)? = null// 答案变化后的回调


    fun setLayoutRes(@LayoutRes layoutRes: Int): FancyDialogFragment {
        mLayoutRes = layoutRes
        return this
    }

    fun setCanCancelOutside(bool: Boolean): FancyDialogFragment {
        canCancelOutside = bool
        return this
    }

    fun setBottomMode(bool: Boolean): FancyDialogFragment {
        isBottomMode = bool
        return this
    }

    fun setWidth(context: Context, widthDip: Int): FancyDialogFragment {
        width = context.dip(widthDip)
        return this
    }

    fun setHeight(context: Context, heightDip: Int): FancyDialogFragment {
        height = context.dip(heightDip)
        return this
    }

    fun setViewListener(listener: ((FancyDialogFragment, ViewDataBinding) -> Unit)): FancyDialogFragment {
        this.mListener = listener
        return this
    }

    override fun onStart() {
        super.onStart()
        val window = dialog.window
        window ?: return

        val params = window.attributes
        if (isBottomMode) {// 底部模式
            params.gravity = Gravity.BOTTOM
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
        } else {
            params.dimAmount = DEFAULT_DIM
            params.height = height
            params.width = width
        }
        window.attributes = params
        // 这里用透明颜色替换掉系统自带背景
        val color = ContextCompat.getColor(requireContext(), android.R.color.transparent)
        window.setBackgroundDrawable(ColorDrawable(color))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(canCancelOutside)

        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, mLayoutRes, container, false)
        mListener?.invoke(this, binding)
        return binding.root
    }
}
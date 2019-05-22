package xiaofu.lib.base.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import xiaofu.lib.base.R

/**
 *
 * Created by @author xiaofu on 2019/1/7.
 */
abstract class BaseBindAdapter<T>
constructor(layoutId: Int)
    : BaseQuickAdapter<T, BaseBindAdapter.BaseBindHolder>(layoutId, null) {

    private lateinit var skeletonScreen: RecyclerViewSkeletonScreen

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding: ViewDataBinding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false)
                ?: return super.getItemView(layoutResId, parent)
        val view = binding.root
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding)// 这个ID唯一即可，可自由生成
        return view
    }

    class BaseBindHolder(view: View?) : BaseViewHolder(view) {
        fun getBinding(): ViewDataBinding {
            return itemView.getTag(R.id.BaseQuickAdapter_databinding_support) as ViewDataBinding
        }
    }

    /**
     * 骨架屏配置参数
     * shimmer(true)    // whether show shimmer animation.                      default is true
     * count(10)        // the recycler view item count.                        default is 10
     * color(color)     // the shimmer color.                                   default is #a2878787
     * angle(20)        // the shimmer angle.                                   default is 20
     * duration(1000)   // the shimmer animation duration.                      default is 1000
     * frozen(false)    // whether frozen recyclerView during skeleton showing  default is true
     */
    fun showSkeleton(rv: RecyclerView, @LayoutRes holderLayout: Int) {
        if (::skeletonScreen.isInitialized) {
            skeletonScreen.show()
        } else {
            skeletonScreen = Skeleton.bind(rv)
                    .adapter(this)
                    .load(holderLayout)
                    .shimmer(false)
                    .show()
        }
    }

    fun hideSkeleton() {
        if (::skeletonScreen.isInitialized) {
            skeletonScreen.hide()
        }
    }
}
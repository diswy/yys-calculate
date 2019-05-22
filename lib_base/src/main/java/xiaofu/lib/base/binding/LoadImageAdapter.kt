package xiaofu.lib.base.binding

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import xiaofu.lib.inline.loadUrl
import xiaofu.lib.inline.loadUrlCorner

/**
 *
 * Created by @author xiaofu on 2019/1/7.
 */
object LoadImageAdapter {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(imageView: ImageView, url: String?) {
        if (!TextUtils.isEmpty(url)) {
            imageView.loadUrl(imageView.context, url)
        }
    }

    @BindingAdapter("imageUrlCorner")
    @JvmStatic
    fun setCornerImageUrl(imageView: ImageView, url: String?) {
        if (!TextUtils.isEmpty(url)) {
            imageView.loadUrlCorner(imageView.context, url)
        }
    }

}
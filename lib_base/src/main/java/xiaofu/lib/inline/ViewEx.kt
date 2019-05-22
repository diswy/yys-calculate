package xiaofu.lib.inline

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.jetbrains.anko.dip
import xiaofu.lib.tools.GlideApp
import java.io.File

///**
// * kotlin协程导致热修复冲突，妥协关闭
// * 幽雅的过滤重复点击，默认300ms
// * @param time 需要过滤的时间，单位ms
// */
//@UseExperimental(ObsoleteCoroutinesApi::class)
//fun View.onClick(time: Long = 300L, action: suspend (View) -> Unit) {
//    // launch one actor
//    val eventActor = GlobalScope.actor<View>(Dispatchers.Main) {
//        for (event in channel) {
//            action(event)
//            delay(time)
//        }
//    }
//    // install a listener to activate this actor
//    setOnClickListener {
//        eventActor.offer(it)
//    }
//}

/**
 * 使用Glide加载图片
 */
fun ImageView.loadUrl(context: Context, url: String?) {
    GlideApp.with(context).load(url).fitCenter().into(this)
}

fun ImageView.loadUrl(context: Context, res: Int?) {
    GlideApp.with(context).load(res).fitCenter().into(this)
}

fun ImageView.loadUrl(context: Context, uri: Uri?) {
    GlideApp.with(context).load(uri).fitCenter().into(this)
}

fun ImageView.loadUrl(context: Context, file: File?) {
    GlideApp.with(context).load(file).fitCenter().into(this)
}

fun ImageView.loadUrl(context: Context, bitmap: Bitmap?) {
    GlideApp.with(context).load(bitmap).fitCenter().into(this)
}

fun ImageView.loadUrl(context: Context, drawable: Drawable?) {
    GlideApp.with(context).load(drawable).fitCenter().into(this)
}

fun ImageView.loadUrl(context: Context, @DrawableRes res: Int) {
    GlideApp.with(context).load(ContextCompat.getDrawable(context, res)).fitCenter().into(this)
}

fun ImageView.loadUrlCorner(context: Context, url: String?) {
    val roundCorner = RoundedCorners(context.dip(4))
    val options = RequestOptions.bitmapTransform(roundCorner)
    GlideApp.with(context).load(url).fitCenter().apply(options).into(this)
}

/**
 * 使用Glide高质量加载图片，更消耗资源
 */
fun ImageView.loadUrlHigh(context: Context, url: String?) {
    GlideApp.with(context).asBitmap().format(DecodeFormat.PREFER_ARGB_8888).load(url).fitCenter().into(this)
}

fun ImageView.loadUrlHigh(context: Context, res: Int?) {
    GlideApp.with(context).asBitmap().format(DecodeFormat.PREFER_ARGB_8888).load(res).fitCenter().into(this)
}

fun ImageView.loadUrlHigh(context: Context, uri: Uri?) {
    GlideApp.with(context).asBitmap().format(DecodeFormat.PREFER_ARGB_8888).load(uri).fitCenter().into(this)
}

fun ImageView.loadUrlHigh(context: Context, file: File?) {
    GlideApp.with(context).asBitmap().format(DecodeFormat.PREFER_ARGB_8888).load(file).fitCenter().into(this)
}

fun ImageView.loadUrlHigh(context: Context, bitmap: Bitmap?) {
    GlideApp.with(context).asBitmap().format(DecodeFormat.PREFER_ARGB_8888).load(bitmap).fitCenter().into(this)
}

fun ImageView.loadUrlHigh(context: Context, drawable: Drawable?) {
    GlideApp.with(context).asBitmap().format(DecodeFormat.PREFER_ARGB_8888).load(drawable).fitCenter().into(this)
}
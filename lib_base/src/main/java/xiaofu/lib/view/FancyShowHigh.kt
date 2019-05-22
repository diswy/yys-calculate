package xiaofu.lib.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 简单的控件，画一个可以镂空的蒙层
 * Created by @author xiaofu on 2019/4/19.
 */
class FancyShowHigh @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    private val mShadowPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)// 蒙层画笔
    private var path: Path = Path()// 镂空路径

    init {
        mShadowPaint.color = Color.parseColor("#cc222222")// 蒙层颜色
        path.fillType = Path.FillType.INVERSE_WINDING
    }

    /**
     * 需要镂空的控件
     */
    fun setTargetView(view: View, corner: Int = 0, padding: Int = 0) {
        view.post {
            path.reset()
            val location = IntArray(2)
            view.getLocationInWindow(location)
            val rect = RectF((location[0] - padding).toFloat(),
                    (location[1] - padding).toFloat(),
                    (location[0] + view.width + padding).toFloat(),
                    (location[1] + view.height + padding).toFloat())
            path.addRoundRect(rect, corner.toFloat(), corner.toFloat(), Path.Direction.CW)
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

        canvas.save()
        canvas.clipPath(path)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mShadowPaint)
        canvas.restore()
    }
}
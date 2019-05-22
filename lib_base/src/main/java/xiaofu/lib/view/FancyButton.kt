package xiaofu.lib.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import xiaofu.lib.base.R
import xiaofu.lib.view.drawable.Constants
import xiaofu.lib.view.drawable.DrawableBuilder

/**
 *
 * Created by @author xiaofu on 2018/12/14.
 */
class FancyButton @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : AppCompatButton(context, attrs, defStyleAttr) {

    private var mDrawable: Drawable? = null

    private var mShape: Int = Constants.RECT// 样式
    private var mBorderType: Int = Constants.LINE// 边框样式
    private var mBorderWidth: Float = 0f// 边框宽度
    private var mBorderColor: Int = Constants.DEFAULT_BORDER// 边框颜色
    private var mSolidNormal: Int = Constants.DEFAULT_TRANSPARENT// 实体颜色普通
    private var mSolidPressed: Int = Constants.DEFAULT_PRESSED// 实体颜色按压状态
    private var mCornerRadius: Float = Constants.CORNER_RADIUS// 默认圆角为0
    private var mUnEnableColor: Int = Constants.DEFAULT_UNENABLE// 默认不可用颜色
    private var mRipple: Boolean = true// 是否使用水波纹效果

    init {
        if (context != null && attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.FancyButton)
            mShape = a.getInteger(R.styleable.FancyButton_fb_shape, Constants.RECT)
            mBorderType = a.getInteger(R.styleable.FancyButton_fb_border_type, Constants.LINE)
            mBorderWidth = a.getDimension(R.styleable.FancyButton_fb_border_width, 0f)
            mBorderColor = a.getInteger(R.styleable.FancyButton_fb_border_color, Constants.DEFAULT_BORDER)
            mSolidNormal = a.getInteger(R.styleable.FancyButton_fb_solid_normal, Constants.DEFAULT_TRANSPARENT)
            mSolidPressed = a.getInteger(R.styleable.FancyButton_fb_solid_pressed, Constants.DEFAULT_PRESSED)
            mCornerRadius = a.getDimension(R.styleable.FancyButton_fb_corner_radius, Constants.CORNER_RADIUS)
            mUnEnableColor = a.getInteger(R.styleable.FancyButton_fb_un_enable_color, Constants.DEFAULT_UNENABLE)
            mRipple = a.getBoolean(R.styleable.FancyButton_fb_ripple, true)
            a.recycle()

            val builder = DrawableBuilder()
            when (mShape) {
                Constants.RECT -> builder.rectangle().cornerRadius(mCornerRadius.toInt())
                Constants.ROUNDED -> builder.rounded()
                Constants.OVAL -> builder.oval()
            }
            when (mBorderType) {
                Constants.LINE -> builder.hairlineBordered()
                Constants.DASH -> builder.dashed()
            }
            mDrawable = builder.strokeWidth(mBorderWidth.toInt())
                    .strokeColor(mBorderColor)
                    .solidColor(mSolidNormal)
                    .solidColorPressed(if (mSolidPressed == Constants.DEFAULT_PRESSED) mSolidNormal else mSolidPressed)
                    .solidColorDisabled(mUnEnableColor)
                    .ripple(mRipple)
                    .build()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (!isInEditMode) {
            background = mDrawable
        }
    }
}
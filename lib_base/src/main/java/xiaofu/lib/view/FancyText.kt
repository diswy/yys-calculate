package xiaofu.lib.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.TextView
import xiaofu.lib.base.R
import xiaofu.lib.view.drawable.ColorStateListTool

/**
 *
 * Created by @author xiaofu on 2018/12/15.
 */
class FancyText @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : TextView(context, attrs, defStyleAttr) {

    private var mColorStateList: ColorStateList? = null

    private var mPressedColor: Int = -1// 按压中颜色
    private var mNormalColor: Int = -1// 普通颜色,或者直接使用其父类的字体颜色

    init {

        if (context != null && attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.FancyText)
            val colorTool = ColorStateListTool()
            mPressedColor = a.getInteger(R.styleable.FancyText_ft_color_pressed, -1)
            mNormalColor = a.getInteger(R.styleable.FancyText_ft_color_normal, -1)
            if (mNormalColor == -1) {
                mNormalColor = currentTextColor
            }
            if (mPressedColor != -1) {// 设置了按压颜色，否则mColorStateList为空不进行绘制
                mColorStateList = colorTool.createSimple(mPressedColor, mNormalColor)
            }
            a.recycle()
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (!isInEditMode && mColorStateList != null) {// actually if it's null,it will throw a exception
            setTextColor(mColorStateList)
        }
    }

}
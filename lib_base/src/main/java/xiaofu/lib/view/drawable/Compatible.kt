package xiaofu.lib.view.drawable

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.RotateDrawable
import android.os.Build
import java.lang.reflect.Field
import java.lang.reflect.Method

private val gradientState = resolveGradientState()

private fun resolveGradientState(): Class<*> {
    val classes = GradientDrawable::class.java.declaredClasses
    for (singleClass in classes) {
        if (singleClass.simpleName == "GradientState") return singleClass
    }
    throw RuntimeException("GradientState could not be found in current GradientDrawable implementation")
}

private val rotateState = resolveRotateState()

private fun resolveRotateState(): Class<*> {
    val classes = RotateDrawable::class.java.declaredClasses
    for (singleClass in classes) {
        if (singleClass.simpleName == "RotateState") return singleClass
    }
    throw RuntimeException("RotateState could not be found in current RotateDrawable implementation")
}

@Throws(SecurityException::class, NoSuchFieldException::class)
private fun resolveField(source: Class<*>, fieldName: String): Field {
    val field = source.getDeclaredField(fieldName)
    field.isAccessible = true
    return field
}

@Throws(SecurityException::class, NoSuchMethodException::class)
private fun resolveMethod(source: Class<*>, methodName: String, vararg parameterTypes: Class<*>): Method {
    val method = source.getDeclaredMethod(methodName, *parameterTypes)
    method.isAccessible = true
    return method
}

fun setInnerRadius(drawable: GradientDrawable, value: Int) {
    try {
        val innerRadius = resolveField(gradientState, "mInnerRadius")
        innerRadius.setInt(drawable.constantState, value)
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
}

fun setInnerRadiusRatio(drawable: GradientDrawable, value: Float) {
    try {
        val innerRadius = resolveField(gradientState, "mInnerRadiusRatio")
        innerRadius.setFloat(drawable.constantState, value)
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
}

fun setThickness(drawable: GradientDrawable, value: Int) {
    try {
        val innerRadius = resolveField(gradientState, "mThickness")
        innerRadius.setInt(drawable.constantState, value)
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
}

fun setThicknessRatio(drawable: GradientDrawable, value: Float) {
    try {
        val innerRadius = resolveField(gradientState, "mThicknessRatio")
        innerRadius.setFloat(drawable.constantState, value)
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
}

fun setUseLevelForShape(drawable: GradientDrawable, value: Boolean) {
    try {
        val useLevelForShape = resolveField(gradientState, "mUseLevelForShape")
        useLevelForShape.setBoolean(drawable.constantState, value)
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
}

fun setOrientation(drawable: GradientDrawable, value: GradientDrawable.Orientation) {
    drawable.orientation = value
}

fun setColors(drawable: GradientDrawable, value: IntArray) {
    drawable.colors = value
}

fun setGradientRadiusType(drawable: GradientDrawable, value: Int) {
    try {
        val type = resolveField(gradientState, "mGradientRadiusType")
        type.setInt(drawable.constantState, value)
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
}

fun setGradientRadius(drawable: GradientDrawable, value: Float) {
    try {
        val gradientRadius = resolveField(gradientState, "mGradientRadius")
        gradientRadius.setFloat(drawable.constantState, value)
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
}

fun setStrokeColor(drawable: GradientDrawable, value: Int) {
    try {
        val type = resolveField(gradientState, "mStrokeColor")
        type.setInt(drawable.constantState, value)
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    } catch (e: IllegalAccessException) {
        e.printStackTrace()
    }
}

fun setDrawable(rotateDrawable: RotateDrawable, drawable: Drawable) {
    rotateDrawable.drawable = drawable
}

fun setPivotX(rotateDrawable: RotateDrawable, value: Float) {
    rotateDrawable.pivotX = value
}

fun setPivotY(rotateDrawable: RotateDrawable, value: Float) {
    rotateDrawable.pivotY = value
}

fun setFromDegrees(rotateDrawable: RotateDrawable, value: Float) {
    rotateDrawable.fromDegrees = value
}

fun setToDegrees(rotateDrawable: RotateDrawable, value: Float) {
    rotateDrawable.toDegrees = value
}

fun setRadius(rippleDrawable: RippleDrawable, value: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        rippleDrawable.radius = value
    } else {
        try {
            val setRadiusMethod = resolveMethod(RippleDrawable::class.java, "setMaxRadius", Int::class.java)
            setRadiusMethod.invoke(rippleDrawable, value)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }
}
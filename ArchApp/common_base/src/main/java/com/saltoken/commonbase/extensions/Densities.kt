package com.saltoken.commonbase.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.Dimension
import androidx.annotation.Px
import com.saltoken.commonbase.commonBaseContext


@Px
fun Context.dp2px(@Dimension(unit = Dimension.DP) dpVal: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dpVal, this.resources.displayMetrics
    )
}

@Px
fun Context.sp2px(@Dimension(unit = Dimension.DP) spVal: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        spVal, this.resources.displayMetrics
    )
}

@Dimension(unit = Dimension.DP)
fun Context.px2dp(@Px pxVal: Float): Float {
    val scale = this.resources.displayMetrics.density
    return pxVal / scale
}

@Dimension(unit = Dimension.SP)
fun Context.px2sp(@Px pxVal: Float): Float {
    return pxVal / this.resources.displayMetrics.scaledDensity
}

@Px
fun Context.dp2pxInt(@Dimension(unit = Dimension.DP) dpVal: Float): Int {
    return dp2px(dpVal).toInt()
}

@Px
fun Context.sp2pxInt(@Dimension(unit = Dimension.DP) spVal: Float): Int {
    return sp2px(spVal).toInt()
}

@Dimension(unit = Dimension.DP)
fun Context.px2dpInt(@Px pxVal: Float): Int {
    return px2dp(pxVal).toInt()
}

@Dimension(unit = Dimension.SP)
fun Context.px2spInt(@Px pxVal: Float): Int {
    return px2spInt(pxVal).toInt()
}

fun Float.dp(context: Context):Float=context.dp2px(this)

fun Float.sp(context: Context):Float=context.sp2px(this)

fun Int.dp(context: Context):Float=context.dp2px(this.toFloat())

fun Int.sp(context: Context):Float=context.sp2px(this.toFloat())

fun Float.dpInt(context: Context):Int=context.dp2pxInt(this)

fun Float.spInt(context: Context):Int=context.sp2pxInt(this)

fun Int.dpInt(context: Context):Int=context.dp2pxInt(this.toFloat())

fun Int.spInt(context: Context):Int=context.sp2pxInt(this.toFloat())

val  Float.dp:Float get() = commonBaseContext.dp2px(this)

val  Float.sp:Float get() = commonBaseContext.sp2px(this)

val  Int.dp:Float get() = commonBaseContext.dp2px(this.toFloat())

val  Int.sp:Float get() = commonBaseContext.sp2px(this.toFloat())

val  Float.dpInt:Int get() = commonBaseContext.dp2pxInt(this)

val  Float.spInt:Int get() = commonBaseContext.sp2pxInt(this)

val  Int.dpInt:Int get() = commonBaseContext.dp2pxInt(this.toFloat())

val  Int.spInt:Int get() = commonBaseContext.sp2pxInt(this.toFloat())

package ru.deltadelete.lab10.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import kotlin.math.roundToInt

public val Int.dp: Int
    get() {
        val density = Resources.getSystem().displayMetrics.densityDpi.toFloat()
        return this * (density / DisplayMetrics.DENSITY_DEFAULT).roundToInt()
    }
package ru.deltadelete.lab11.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import kotlin.math.roundToInt

val Int.dp: Int
    get() {
        val density = Resources.getSystem().displayMetrics.densityDpi.toFloat()
        return this * (density / DisplayMetrics.DENSITY_DEFAULT).roundToInt()
    }

var <T> ObservableField<T>.value: T?
    get() = this.get()
    set(value) = this.set(value)

fun Observable.addOnPropertyChangedCallback(function: (Observable, Int) -> Unit) {
    this.addOnPropertyChangedCallback(
        object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                function(sender, propertyId)
            }
        }
    )
}
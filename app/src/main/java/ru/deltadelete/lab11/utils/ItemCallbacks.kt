package ru.deltadelete.lab11.utils

import android.view.View

class ItemCallbacks<T> {
    var onItemClick: ((T, View) -> Unit)? = null
    var onLongItemClick: ((T, View) -> Boolean)? = null

    fun click(item: T, view: View) {
        onItemClick?.invoke(item, view)
    }

    fun longClick(item: T, view: View): Boolean {
        return onLongItemClick?.invoke(item, view) == true
    }
}
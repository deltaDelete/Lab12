package ru.deltadelete.lab10.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object BindingAdapters {
    @BindingAdapter(
        value = ["imageUrl", "error"],
        requireAll = false
    )
    @JvmStatic fun setImageFromCode(view: ImageView, url: String?, @DrawableRes error: Int) {
        if (url == null) {
            view.setImageResource(error)
        }
        else {
            Picasso.get()
                .load(url)
                .error(error)
                .into(view)
        }
    }
}
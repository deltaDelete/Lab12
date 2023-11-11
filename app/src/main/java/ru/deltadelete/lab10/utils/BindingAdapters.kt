package ru.deltadelete.lab10.utils

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ru.deltadelete.lab10.R

public object BindingAdapters {
    @BindingAdapter(
        value = ["imageUrl"],
        requireAll = false
    )
    @JvmStatic fun setImageFromCode(view: ImageView, url: String?) {
        if (url == null) {
            view.setImageResource(R.drawable.baseline_error_outline_24)
        }
        else {
            Picasso.get()
                .load(url)
                .error(R.drawable.baseline_error_outline_24)
                .into(view, CallbackError)
        }
    }

    @BindingAdapter(
        value = ["android:adapter"]
    )
    @JvmStatic fun setRecyclerViewAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        view.adapter = adapter
    }

    object CallbackError : Callback {
        override fun onSuccess() {
            Log.d("PICASSO", "Successfully loaded image from imageUrl data binding")
        }
        override fun onError(e: java.lang.Exception?) {
            Log.e("PICASSO", "Error loading image from imageUrl data binding")
            e?.apply { printStackTrace() }
        }
    }
}
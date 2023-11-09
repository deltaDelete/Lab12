package ru.deltadelete.lab10.ui

import android.view.View
import android.view.View.OnClickListener
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class CountryListViewModel : ViewModel() {
    val text = ObservableField<String>("Sample text")

    public fun firstButtonClick(view: View)  {
        text.set("Hello from button!")
    }
}
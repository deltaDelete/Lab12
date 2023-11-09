package ru.deltadelete.lab10.ui.country_list

import android.view.View
import android.view.View.OnClickListener
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class CountryListViewModel : ViewModel() {
    val text = ObservableField<String>("Sample text")

    public fun firstButtonClick(view: View) {
        text.set("Hello from button!")
    }
}
package ru.deltadelete.lab10.ui.country_list

import android.app.Application
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.flow.MutableStateFlow
import ru.deltadelete.lab10.R
import ru.deltadelete.lab10.adapters.CountryAdapter
import ru.deltadelete.lab10.database.AppDatabase
import ru.deltadelete.lab10.database.entities.Country

class CountryListViewModel(app: Application) : AndroidViewModel(app) {
    val text = ObservableField<String>("Sample text")
    private lateinit var items: ObservableArrayList<Country>
    val adapter = CountryAdapter(getApplication<Application>().applicationContext, items)

    private val database : AppDatabase = Room.databaseBuilder(getApplication<Application>().applicationContext, AppDatabase::class.java, "towns-db").build()

    init {
        items = ObservableArrayList<Country>().apply {
            this.addAll(database.countryDao().all)
        }
    }

    public fun firstButtonClick(view: View) {
        text.set("Hello from button!")
    }
}
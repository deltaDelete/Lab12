package ru.deltadelete.lab10.ui.country_list

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.deltadelete.lab10.adapters.CountryAdapter
import ru.deltadelete.lab10.database.AppDatabase
import ru.deltadelete.lab10.database.entities.Country
import ru.deltadelete.lab10.utils.value

public class CountryListViewModel(application: Application) : AndroidViewModel(application) {
    val text = ObservableField<String>("Sample text")

    //    val items: Flow<List<Country>>
    var adapter: ObservableField<CountryAdapter>

    var onAddCountryClick: ((View) -> Unit)? = null

    lateinit var items: MutableList<Country>

    public val database: AppDatabase

    init {
        val context = getApplication<Application>().applicationContext
        adapter = ObservableField(
            CountryAdapter(context, mutableListOf<Country>())
        )
        database = AppDatabase.getInstance(context)
        viewModelScope.launch(Dispatchers.IO) {
            items = database.countryDao().all().toMutableList()
            adapter.value = CountryAdapter(context, items)
        }

//        ObservableArrayList<Country>().apply {
//            add(Country(1, "Россия", "ru"))
//            add(Country(2, "Казахстан", "kz"))
//        }
    }

    public fun firstButtonClick(view: View) {
        text.set("Hello from button!")
        text.value
    }

    fun addCountryClick(view: View) {
        onAddCountryClick?.invoke(view)
    }
}
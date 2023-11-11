package ru.deltadelete.lab10.ui.country_list

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.deltadelete.lab10.R
import ru.deltadelete.lab10.adapters.CountryAdapter
import ru.deltadelete.lab10.database.AppDatabase
import ru.deltadelete.lab10.database.entities.Country
import ru.deltadelete.lab10.utils.addOnPropertyChangedCallback
import ru.deltadelete.lab10.utils.value

public class CountryListViewModel(application: Application) : AndroidViewModel(application) {
    val text = ObservableField<String>("Кнопка для теста")

    var adapter: ObservableField<CountryAdapter>

    var onAddCountryClick: ((View) -> Unit)? = null

    lateinit var items: MutableList<Country>

    val database: AppDatabase

    init {
        val context = getApplication<Application>().applicationContext
        database = AppDatabase.getInstance(context)
        adapter = ObservableField<CountryAdapter>()
        adapter.addOnPropertyChangedCallback { sender, propertyId ->
            viewModelScope.launch(Dispatchers.IO) {
                items = database.countryDao().all().toMutableList()
                this.launch(Dispatchers.Main) {
                    adapter.value?.addAll(items)
                }
            }
        }
    }

    fun firstButtonClick(view: View) {
        text.value = "Текст изменен"
    }

    fun addCountryClick(view: View) {
        onAddCountryClick?.invoke(view)
    }
}


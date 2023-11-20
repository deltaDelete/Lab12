package ru.deltadelete.lab11.ui.country_list

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.deltadelete.lab11.database.AppDatabase
import ru.deltadelete.lab11.database.entities.Country
import ru.deltadelete.lab11.utils.addOnPropertyChangedCallback
import ru.deltadelete.lab11.utils.value

class CountryListViewModel(application: Application) : AndroidViewModel(application) {
    val text: MutableLiveData<String> = MutableLiveData("Кнопка для теста")

    var onAddCountryClick: ((View) -> Unit)? = null

    val items: MutableLiveData<MutableList<Country>> =
        MutableLiveData(emptyList<Country>().toMutableList())

    val database: AppDatabase

    init {
        val context = getApplication<Application>().applicationContext
        database = AppDatabase.getInstance(context)
        viewModelScope.launch(Dispatchers.IO) {
            items.postValue(database.countryDao().all().toMutableList())
        }
    }

    fun firstButtonClick(view: View) {
        text.value = "Текст изменен"
    }

    fun addCountryClick(view: View) {
        onAddCountryClick?.invoke(view)
    }
}

package ru.deltadelete.lab11.ui.town_list

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.deltadelete.lab11.adapters.TownAdapter
import ru.deltadelete.lab11.database.AppDatabase
import ru.deltadelete.lab11.database.entities.Town
import ru.deltadelete.lab11.utils.addOnPropertyChangedCallback
import ru.deltadelete.lab11.utils.value

public class TownListViewModel(application: Application, private var countryId: Int) :
    AndroidViewModel(application) {
    val text = MutableLiveData<String>("Кнопка для теста")

    var onAddClick: ((View) -> Unit)? = null

    var items: MutableLiveData<MutableList<Town>> = MutableLiveData(emptyList<Town>().toMutableList())

    val database: AppDatabase

    init {
        val context = getApplication<Application>().applicationContext
        database = AppDatabase.getInstance(context)
        viewModelScope.launch(Dispatchers.IO) {
            items.postValue(database.townDao().getAllByCountry(countryId))
        }
    }

    fun addClick(view: View) {
        onAddClick?.invoke(view)
    }
}


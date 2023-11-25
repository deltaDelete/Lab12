package ru.deltadelete.lab12.ui.town_list

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.deltadelete.lab12.database.AppDatabase
import ru.deltadelete.lab12.database.entities.Town
import ru.deltadelete.lab12.utils.asLiveData

public class TownListViewModel(application: Application, private var countryId: Int) :
    AndroidViewModel(application) {

    private val _items: MutableLiveData<MutableList<Town>> =
        MutableLiveData(emptyList<Town>().toMutableList())

    val items: LiveData<MutableList<Town>>
        get() = _items.asLiveData()


    var onAddClick: ((View) -> Unit)? = null


    val database: AppDatabase

    init {
        val context = getApplication<Application>().applicationContext
        database = AppDatabase.getInstance(context)
        viewModelScope.launch(Dispatchers.IO) {
            _items.postValue(database.townDao().getAllByCountry(countryId))
        }
    }

    fun addClick(view: View) {
        onAddClick?.invoke(view)
    }
}


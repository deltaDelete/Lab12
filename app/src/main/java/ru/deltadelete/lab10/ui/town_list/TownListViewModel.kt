package ru.deltadelete.lab10.ui.town_list

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.deltadelete.lab10.adapters.TownAdapter
import ru.deltadelete.lab10.database.AppDatabase
import ru.deltadelete.lab10.database.entities.Town
import ru.deltadelete.lab10.utils.addOnPropertyChangedCallback
import ru.deltadelete.lab10.utils.value

public class TownListViewModel(application: Application, private var countryId: Int) : AndroidViewModel(application) {
    val text = ObservableField<String>("Кнопка для теста")

    var adapter: ObservableField<TownAdapter>

    var onAddClick: ((View) -> Unit)? = null

    lateinit var items: MutableList<Town>

    val database: AppDatabase

    init {
        val context = getApplication<Application>().applicationContext
        adapter = ObservableField()
        database = AppDatabase.getInstance(context)
        adapter.addOnPropertyChangedCallback { _, _ ->
            viewModelScope.launch(Dispatchers.IO) {
                items = database.townDao().getAllByCountry(countryId)
                this.launch(Dispatchers.Main) {
                    adapter.value?.addAll(items)
                }
            }
        }
    }

    fun addClick(view: View) {
        onAddClick?.invoke(view)
    }
}


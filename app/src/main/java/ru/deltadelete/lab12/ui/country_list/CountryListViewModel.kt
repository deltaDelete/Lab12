package ru.deltadelete.lab12.ui.country_list

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.deltadelete.lab12.database.AppDatabase
import ru.deltadelete.lab12.database.entities.Country
import ru.deltadelete.lab12.utils.asLiveData
import kotlin.reflect.KProperty

class CountryListViewModel(application: Application) : AndroidViewModel(application) {
    private val _text: MutableLiveData<String> = MutableLiveData("Кнопка для теста")
    private val _items: MutableLiveData<MutableList<Country>> =
        MutableLiveData(emptyList<Country>().toMutableList())

    val text: LiveData<String>
        get() = _text.asLiveData()

    val items: LiveData<MutableList<Country>>
        get() = _items.asLiveData()

    var onAddCountryClick: ((View) -> Unit)? = null


    val database: AppDatabase

    val sharedPreferences = application.getSharedPreferences(
        CountryListViewModel::class.simpleName!!,
        Context.MODE_PRIVATE
    )
    val prefsEditor = sharedPreferences.edit()
    var list by SharedPrefs("list", "CountryListFragment", "")

    init {
        val context = getApplication<Application>().applicationContext
        database = AppDatabase.getInstance(context)
        viewModelScope.launch(Dispatchers.IO) {
            _items.postValue(database.countryDao().all().toMutableList())
        }
    }

    fun firstButtonClick(view: View) {
        _text.value = "Текст изменен"
    }

    fun addCountryClick(view: View) {
        onAddCountryClick?.invoke(view)
    }

    fun import() {

    }

    fun export() {

    }
}

// TODO SharedPrefs by operator
class SharedPrefs(
    val key: String,
    val store: String,
    private val initialValue: String
) {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor

    operator fun SharedPreferences.setValue(
        viewModel: CountryListViewModel,
        property: KProperty<*>,
        value: String
    )
    {
        checkInit(viewModel.getApplication())
        edit.putString(key, value)
        edit.apply()
    }

    operator fun SharedPreferences.getValue(
        viewModel: CountryListViewModel,
        property: KProperty<*>
    ) {
        checkInit(viewModel.getApplication())
        sharedPreferences.getString(key, initialValue)!!
    }

    private fun checkInit(context: Context): Boolean {
        if (this::sharedPreferences.isInitialized) {
            return true
        }
        sharedPreferences = context.getSharedPreferences(store, Context.MODE_PRIVATE)
        if (this::edit.isInitialized) {
            return true
        }
        edit = sharedPreferences.edit()
        return false
    }
}
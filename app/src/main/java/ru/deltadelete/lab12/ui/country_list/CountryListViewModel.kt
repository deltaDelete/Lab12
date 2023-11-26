package ru.deltadelete.lab12.ui.country_list

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.deltadelete.lab12.R
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

    var list: String by SharedPrefs("list", "CountryListFragment", "[]")

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

    private val gson: Gson = Gson()
    private val itemType = object : TypeToken<List<Country>>() {}.type

    fun import(view: View) {
        try {
            val some: List<Country> = gson.fromJson<List<Country>>(list, itemType) ?: emptyList()
            if (some.isEmpty()) {
                return
            }
            _items.value = some.toMutableList()

            Snackbar.make(
                view,
                R.string.imported, Snackbar.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun export(view: View) {
        list = gson.toJson(items.value)

        Snackbar.make(
            view,
            R.string.exported, Snackbar.LENGTH_LONG
        ).show()
    }
}

class SharedPrefs(
    val key: String,
    val store: String,
    private val initialValue: String
) {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor

    operator fun setValue(
        viewModel: CountryListViewModel,
        property: KProperty<*>,
        value: String
    ) {
        checkInit(viewModel.getApplication())
        edit.putString(key, value)
        edit.apply()
    }

    operator fun getValue(
        viewModel: CountryListViewModel,
        property: KProperty<*>
    ): String {
        checkInit(viewModel.getApplication())
        return sharedPreferences.getString(key, initialValue)!!
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
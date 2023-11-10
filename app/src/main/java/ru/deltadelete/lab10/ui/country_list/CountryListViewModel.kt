package ru.deltadelete.lab10.ui.country_list

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.text.Editable
import android.text.InputFilter
import android.util.DisplayMetrics
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.annotation.Dimension
import androidx.core.view.setPadding
import androidx.core.widget.addTextChangedListener
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.room.Room
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.ripple.RippleUtils
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import ru.deltadelete.lab10.R
import ru.deltadelete.lab10.adapters.CountryAdapter
import ru.deltadelete.lab10.database.AppDatabase
import ru.deltadelete.lab10.database.entities.Country
import kotlin.math.roundToInt
import ru.deltadelete.lab10.utils.dp
import kotlin.reflect.KFunction

public class CountryListViewModel(application: Application) : AndroidViewModel(application) {
    val text = MutableLiveData<String>("Sample text")

    //    val items: Flow<List<Country>>
    var adapter: CountryAdapter? = null

    var onAddCountryClick: ((View) -> Unit)? = null

    public val database: AppDatabase

    init {
        val context = getApplication<Application>().applicationContext
        database = Room.databaseBuilder(context, AppDatabase::class.java, "towns-db").build()
        database.countryDao().all().map { data ->
            adapter = CountryAdapter(context, data)
        }
//        ObservableArrayList<Country>().apply {
//            add(Country(1, "Россия", "ru"))
//            add(Country(2, "Казахстан", "kz"))
//        }
    }

    public fun firstButtonClick(view: View) {
        text.value = "Hello from button!"
    }

    fun addCountryClick(view: View) {
        onAddCountryClick?.invoke(view)
    }
}
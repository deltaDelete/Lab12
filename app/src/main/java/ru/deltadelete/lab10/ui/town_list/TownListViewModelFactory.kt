package ru.deltadelete.lab10.ui.town_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TownListViewModelFactory(
    private var application: Application,
    private var countryId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TownListViewModel::class.java)) {
            return TownListViewModel(application, countryId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class ${modelClass.name}")
    }
}
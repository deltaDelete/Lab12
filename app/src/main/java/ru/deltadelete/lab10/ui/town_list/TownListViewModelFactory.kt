package ru.deltadelete.lab10.ui.town_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TownListViewModelFactory(
    private var application: Application,
    private val countryId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TownListViewModel(application, countryId) as T
    }
}
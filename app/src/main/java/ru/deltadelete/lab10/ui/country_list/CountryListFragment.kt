package ru.deltadelete.lab10.ui.country_list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.deltadelete.lab10.databinding.FragmentCountryListBinding

class CountryListFragment : Fragment() {

    companion object {
        fun newInstance() = CountryListFragment()
    }

    private lateinit var viewModel: CountryListViewModel
    private lateinit var binding: FragmentCountryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = ViewModelProvider(this)[CountryListViewModel::class.java]
    }
}
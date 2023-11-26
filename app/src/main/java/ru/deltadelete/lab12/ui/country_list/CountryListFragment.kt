package ru.deltadelete.lab12.ui.country_list

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.setPadding
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.deltadelete.lab12.R
import ru.deltadelete.lab12.adapters.CountryAdapter
import ru.deltadelete.lab12.database.entities.Country
import ru.deltadelete.lab12.databinding.FragmentCountryListBinding
import ru.deltadelete.lab12.ui.town_list.TownListFragment
import ru.deltadelete.lab12.utils.dp

class CountryListFragment : Fragment() {

    companion object {
        fun newInstance() = CountryListFragment()
    }

    private lateinit var binding: FragmentCountryListBinding
    private lateinit var adapter: CountryAdapter
    private val viewModel: CountryListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        viewModel.onAddCountryClick = this::addCountryClick
        viewModel.items.observe(viewLifecycleOwner) {
            adapter =
                CountryAdapter(requireContext(), it).apply {
                    itemCallbacks.onItemClick = { item, view ->
                        itemClick(item, view)
                    }
                    itemCallbacks.onLongItemClick = { item, view ->
                        longItemClick(item, view)
                    }
                }
            binding.listView.adapter = adapter
        }
        binding.viewModel = viewModel
        requireActivity().addMenuProvider(MenuProvider(), viewLifecycleOwner)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onAddCountryClick = null
        binding.viewModel = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addCountryClick(view: View) {
        val context = requireContext()

        val name: MutableLiveData<String> = MutableLiveData("")
        val code: MutableLiveData<String> = MutableLiveData("")

        val nameEditText = TextInputEditText(context).apply {
            addTextChangedListener { text: Editable? ->
                name.value = text.toString()
                error = if (name.value.isNullOrBlank()) {
                    "Название не может быть пустым"
                } else {
                    null
                }
            }
        }
        val nameInputLayout = TextInputLayout(context).apply {
            hint = "Название"
            addView(nameEditText)
        }

        val codeEditText = TextInputEditText(context).apply {
            filters = arrayOf<InputFilter>(
                InputFilter.LengthFilter(2)
            )
            addTextChangedListener { text: Editable? ->
                code.value = text.toString()
                error = if (code.value.isNullOrBlank()) {
                    "Код страны не может быть пустым"
                } else {
                    null
                }
            }
        }
        val codeInputLayout = TextInputLayout(context).apply {
            hint = "Код страны"
            addView(codeEditText)
        }

        MaterialAlertDialogBuilder(context).apply {
            setView(LinearLayout(this.context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(16.dp)
                addView(nameInputLayout)
                addView(codeInputLayout)
            })
            setTitle("Добавить страну")
            setPositiveButton("Добавить") { _, _ ->
                if (nameInputLayout.error != null || codeInputLayout.error != null) {
                    return@setPositiveButton
                }
                var item = Country(name = name.value!!, code = code.value!!)
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.database.countryDao()?.insert(item)?.let {
                        item = item.copy(id = it.toInt())
                    }
                    launch(Dispatchers.Main) {
                        adapter.add(item)
                    }
                }
            }
            setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()
    }

    private fun itemClick(item: Country, view: View) {
        Snackbar.make(view, item.toString(), Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
        val args = Bundle().apply {
            this.putInt(TownListFragment.Companion.COUNTRY_ID_ARGUMENT, item.id)
        }
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_CountryListFragment_to_townListFragment, args)
    }

    private fun longItemClick(item: Country, view: View): Boolean {
        val context = requireContext()

        MaterialAlertDialogBuilder(context).apply {
            setTitle(R.string.remove_country)
            setMessage(getString(R.string.remove_country_message, item.name))
            setPositiveButton(R.string.remove) { _, _ ->
                adapter.remove(item)
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.database.countryDao()?.delete(item)
                }

                Snackbar.make(
                    view,
                    getString(R.string.removed_country, item.name), Snackbar.LENGTH_LONG
                )
                    .setAction("Action", null).show()
            }
            setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()
        return true;
    }

    inner class MenuProvider : androidx.core.view.MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.menu_list_fragment, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.export_entry -> onExportSelected()

                R.id.import_entry -> onImportSelected()

                else -> false
            }
        }

    }

    fun onExportSelected(): Boolean {
        viewModel.export(requireView())
        return true
    }

    fun onImportSelected(): Boolean {
        viewModel.import(requireView())
        return true
    }
}
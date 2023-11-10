package ru.deltadelete.lab10.ui.country_list

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import androidx.fragment.app.Fragment
import androidx.core.view.setPadding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import ru.deltadelete.lab10.database.entities.Country
import ru.deltadelete.lab10.databinding.FragmentCountryListBinding
import ru.deltadelete.lab10.utils.dp

class CountryListFragment : Fragment() {

    companion object {
        fun newInstance() = CountryListFragment()
    }

    private var binding: FragmentCountryListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel = ViewModelProvider(requireActivity())[CountryListViewModel::class.java]
        binding?.viewModel?.onAddCountryClick = this::addCountryClick
    }

    public fun addCountryClick(view: View) {
        val context = requireContext()

        val name: MutableLiveData<String> = MutableLiveData("")
        val code: MutableLiveData<String> = MutableLiveData("")
        val isValid: MutableLiveData<Boolean> = MutableLiveData(false)

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
                val item = Country(name = name.value!!, code = code.value!!)
                runBlocking {
                    binding?.viewModel?.database?.countryDao()?.insertAll(item)
                }
                binding?.viewModel?.adapter?.add(item)
            }
            setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.viewModel?.onAddCountryClick = null
        binding?.viewModel = null
    }
}
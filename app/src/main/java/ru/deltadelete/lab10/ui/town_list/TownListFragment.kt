package ru.deltadelete.lab10.ui.town_list

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.setPadding
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.deltadelete.lab10.R
import ru.deltadelete.lab10.adapters.TownAdapter
import ru.deltadelete.lab10.database.entities.Town
import ru.deltadelete.lab10.databinding.FragmentTownListBinding
import ru.deltadelete.lab10.utils.dp
import ru.deltadelete.lab10.utils.value

class TownListFragment : Fragment() {

    companion object {
        const val COUNTRY_ID_ARGUMENT = "COUNTRY_ID_ARGUMENT"
        fun newInstance() = TownListFragment()
    }

    private var countryId: Int = 0

    private var binding: FragmentTownListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTownListBinding.inflate(inflater, container, false)
        countryId = arguments?.getInt(COUNTRY_ID_ARGUMENT) ?: 0
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.viewModel?.onAddClick = null
        binding?.viewModel?.adapter?.value = null
        binding?.viewModel = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        binding?.viewModel = ViewModelProvider(
            this,
            TownListViewModelFactory(activity.application, countryId)
            )[TownListViewModel::class.java]
        binding?.viewModel?.onAddClick = this::addClick
        binding?.viewModel?.adapter?.value =
        TownAdapter(view.context, emptyList<Town>().toMutableList()).apply {
            itemCallbacks.onItemClick = { item, view ->
                itemClick(item, view)
            }
            itemCallbacks.onLongItemClick = { item, view ->
                longItemClick(item, view)
            }
        }
    }

    fun addClick(view: View) {
        val context = requireContext()

        val name: MutableLiveData<String> = MutableLiveData("")
        val desc: MutableLiveData<String> = MutableLiveData("")

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

        val descEditText = TextInputEditText(context).apply {
            addTextChangedListener { text: Editable? ->
                desc.value = text.toString()
                error = if (desc.value.isNullOrBlank()) {
                    "Описание не может быть пустым"
                } else {
                    null
                }
            }
        }
        val descInputLayout = TextInputLayout(context).apply {
            hint = "Описание"
            addView(descEditText)
        }

        MaterialAlertDialogBuilder(context).apply {
            setView(LinearLayout(this.context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(16.dp)
                addView(nameInputLayout)
                addView(descInputLayout)
            })
            setTitle("Добавить город")
            setPositiveButton("Добавить") { _, _ ->
                if (nameInputLayout.error != null || descInputLayout.error != null) {
                    return@setPositiveButton
                }
                val item = Town(name = name.value!!, description = desc.value!!, countryId = countryId)
                binding?.viewModel?.adapter?.value?.add(item)
                lifecycleScope.launch(Dispatchers.IO) {
                    binding?.viewModel?.database?.townDao()?.insertAll(item)
                }
            }
            setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()
    }

    private fun itemClick(item: Town, view: View) {
        Snackbar.make(view, item.toString(), Snackbar.LENGTH_LONG)
            .setAnchorView(R.id.fab)
            .setAction("Action", null).show()
    }

    private fun longItemClick(item: Town, view: View): Boolean {
        val context = requireContext()

        MaterialAlertDialogBuilder(context).apply {
            setTitle(getString(R.string.remove_town))
            setMessage(getString(R.string.remove_town_message, item.name))
            setPositiveButton(getString(R.string.remove)) { _, _ ->
                binding?.viewModel?.adapter?.value?.remove(item)
                lifecycleScope.launch(Dispatchers.IO) {
                    binding?.viewModel?.database?.townDao()?.delete(item)
                }

                Snackbar.make(view,
                    getString(R.string.removed_town, item.name), Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.fab)
                    .setAction("Action", null).show()
            }
            setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()
        return true;
    }
}
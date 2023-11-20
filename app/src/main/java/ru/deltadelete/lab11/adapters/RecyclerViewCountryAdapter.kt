package ru.deltadelete.lab11.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.deltadelete.lab11.R
import ru.deltadelete.lab11.database.entities.Country
import ru.deltadelete.lab11.databinding.CountryItemBinding

class RecyclerViewCountryAdapter {
    class Adapter(private val dataSet: MutableList<Country>) :
        RecyclerView.Adapter<Adapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val binding: CountryItemBinding

            init {
                binding = CountryItemBinding.bind(view)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.country_item, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.binding.item = dataSet[position]
            holder.binding.notifyChange()
        }

        override fun getItemCount(): Int {
            return dataSet.size
        }

        fun add(item: Country) {
            dataSet.add(item)
        }


        fun remove(item: Country) {
            dataSet.remove(item)
        }

        fun addAll(vararg items: Country) {
            dataSet.addAll(items)
        }

        fun removeAll(vararg items: Country) {
            dataSet.removeAll(items)
        }

    }
}
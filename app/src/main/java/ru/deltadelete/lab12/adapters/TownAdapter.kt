package ru.deltadelete.lab12.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import ru.deltadelete.lab12.R
import ru.deltadelete.lab12.database.entities.Town
import ru.deltadelete.lab12.databinding.TownItemBinding
import ru.deltadelete.lab12.utils.ItemCallbacks

class TownAdapter(context: Context, private val items: MutableList<Town>) :
    ArrayAdapter<Town>(context, R.layout.town_item, items) {
    @LayoutRes
    private val layout: Int = R.layout.town_item
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    var itemCallbacks: ItemCallbacks<Town> = ItemCallbacks<Town>()

    @Override
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: TownItemBinding = if (convertView == null) {
            DataBindingUtil.inflate(inflater, layout, parent, false)
        } else {
            DataBindingUtil.bind(convertView)!!
        }

        val item = getItem(position)
        binding.item = item
        binding.itemCallbacks = itemCallbacks

        return binding.rootItem
    }

    override fun getItem(position: Int): Town {
        return items[position]
    }

    override fun add(item: Town?) {
        item?.let {
            super.add(it)
        }
    }

    override fun remove(item: Town?) {
        item?.let {
            items.remove(it)
            super.remove(it)
            notifyDataSetChanged()
        }
    }
}
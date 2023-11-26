package ru.deltadelete.lab12.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import com.google.android.material.shape.CornerFamily
import ru.deltadelete.lab12.R
import ru.deltadelete.lab12.database.entities.Country
import ru.deltadelete.lab12.databinding.CountryItemBinding
import ru.deltadelete.lab12.utils.ItemCallbacks


public class CountryAdapter(context: Context, private val items: MutableList<Country>) :
    ArrayAdapter<Country>(context, R.layout.country_item, items) {
    @LayoutRes
    private val layout: Int = R.layout.country_item
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    var itemCallbacks: ItemCallbacks<Country> = ItemCallbacks<Country>()

    @Override
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: CountryItemBinding = if (convertView == null) {
            DataBindingUtil.inflate(inflater, layout, parent, false)
        } else {
            DataBindingUtil.bind<CountryItemBinding>(convertView)!!
        }

        val item: Country = getItem(position)
        binding.item = item
        binding.itemCallbacks = itemCallbacks
        if (position == 0 && items.size > 1) {
            val radius: Float = binding.root.resources.getDimension(R.dimen.corner_radius_primary)
            val radiusSecondary: Float = binding.root.resources.getDimension(R.dimen.corner_radius_secondary)
            binding.card.shapeAppearanceModel = binding.card.shapeAppearanceModel.toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                .setTopRightCorner(CornerFamily.ROUNDED, radius)
                .setBottomLeftCorner(CornerFamily.ROUNDED, radiusSecondary)
                .setBottomRightCorner(CornerFamily.ROUNDED, radiusSecondary)
                .build()
        }
        else if (position == 0 && items.size == 1) {
            val radius: Float = binding.root.resources.getDimension(R.dimen.corner_radius_primary)
            binding.card.shapeAppearanceModel = binding.card.shapeAppearanceModel.toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                .setTopRightCorner(CornerFamily.ROUNDED, radius)
                .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                .setBottomRightCorner(CornerFamily.ROUNDED, radius)
                .build()
        }
        else if (position == items.lastIndex) {
            val radius: Float = binding.root.resources.getDimension(R.dimen.corner_radius_primary)
            val radiusSecondary: Float = binding.root.resources.getDimension(R.dimen.corner_radius_secondary)
            binding.card.shapeAppearanceModel = binding.card.shapeAppearanceModel.toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, radiusSecondary)
                .setTopRightCorner(CornerFamily.ROUNDED, radiusSecondary)
                .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                .setBottomRightCorner(CornerFamily.ROUNDED, radius)
                .build()
        }

        return binding.root
    }

    override fun getItem(position: Int): Country {
        return items[position]
    }

    override fun add(item: Country?) {
        item?.let {
            super.add(it)
        }
    }

    override fun remove(item: Country?) {
        item?.let {
            items.remove(it)
            super.remove(it)
            notifyDataSetChanged()
        }
    }
}
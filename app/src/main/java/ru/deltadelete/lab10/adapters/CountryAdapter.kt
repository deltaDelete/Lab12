package ru.deltadelete.lab10.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import com.squareup.picasso.Picasso
import ru.deltadelete.lab10.R
import ru.deltadelete.lab10.database.entities.Country
import ru.deltadelete.lab10.databinding.CountryItemBinding

public class CountryAdapter(context: Context, private val items: MutableList<Country>) :
    ArrayAdapter<Country>(context, R.layout.country_item, items) {
    @LayoutRes
    private val layout: Int = R.layout.country_item
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    @Override
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: CountryItemBinding = if (convertView == null) {
            DataBindingUtil.inflate(inflater, R.layout.country_item, parent, false)
        } else {
            DataBindingUtil.bind<CountryItemBinding>(convertView)!!
        }
        // val view: View = convertView ?: inflater.inflate(layout, parent, false)

        val item: Country = getItem(position)
        binding.item = item

//        val text: TextView = view.findViewById(R.id.text_view_name);
//        val image: ImageView = view.findViewById(R.id.image_view_flag);

//        text.text = item.name
//        Picasso.get()
//                .load(item.flagUrl)
//                .error(R.drawable.baseline_error_outline_24)
//                .into(image)
//
//        return view
        return binding.rootCountryItem
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
        }
    }
}
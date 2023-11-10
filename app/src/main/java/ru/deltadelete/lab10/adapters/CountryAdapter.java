package ru.deltadelete.lab10.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import ru.deltadelete.lab10.R;
import ru.deltadelete.lab10.database.entities.Country;
import ru.deltadelete.lab10.databinding.CountryItemBinding;

public class CountryAdapter extends ArrayAdapter<Country> {

    private final List<Country> items;
    @LayoutRes
    private static final int layout = R.layout.country_item;
    private final LayoutInflater inflater = LayoutInflater.from(getContext());

    public CountryAdapter(Context context, List<Country> items) {
        super(context, layout, items);
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CountryItemBinding binding;
        if (convertView == null) {
            binding = CountryItemBinding.inflate(inflater, parent, false);
        } else {
            binding = CountryItemBinding.bind(convertView);
        }

        binding.setItem(getItem(position));

        return binding.getRoot();
    }

    @Nullable
    @Override
    public Country getItem(int position) {
        return items.get(position);
    }

    @Override
    public void add(@Nullable Country object) {
        if (object != null) {
            items.add(object);
        }
        super.add(object);
    }

    @Override
    public void remove(@Nullable Country object) {
        if (object != null) {
            items.remove(object);
        }
        super.remove(object);
    }
}

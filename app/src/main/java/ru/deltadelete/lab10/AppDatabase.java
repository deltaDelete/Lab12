package ru.deltadelete.lab10;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.deltadelete.lab10.entities.Country;
import ru.deltadelete.lab10.entities.District;
import ru.deltadelete.lab10.entities.Landmark;
import ru.deltadelete.lab10.entities.Shop;
import ru.deltadelete.lab10.entities.Town;
import ru.deltadelete.lab10.entities.dao.CountryDao;
import ru.deltadelete.lab10.entities.dao.DistrictDao;
import ru.deltadelete.lab10.entities.dao.TownDao;

@Database(
        entities = {
                Town.class,
                Country.class,
                District.class,
                Landmark.class,
                Shop.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TownDao townDao();
    public abstract DistrictDao districtDao();
    public abstract CountryDao countryDao();
}

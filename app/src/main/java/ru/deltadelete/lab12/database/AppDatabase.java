package ru.deltadelete.lab12.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.deltadelete.lab12.database.dao.CountryDao;
import ru.deltadelete.lab12.database.dao.DistrictDao;
import ru.deltadelete.lab12.database.dao.LandmarkDao;
import ru.deltadelete.lab12.database.dao.ShopDao;
import ru.deltadelete.lab12.database.dao.TownDao;
import ru.deltadelete.lab12.database.entities.Country;
import ru.deltadelete.lab12.database.entities.District;
import ru.deltadelete.lab12.database.entities.Landmark;
import ru.deltadelete.lab12.database.entities.Shop;
import ru.deltadelete.lab12.database.entities.Town;

@Database(
        entities = {
                Town.class,
                Country.class,
                District.class,
                Landmark.class,
                Shop.class
        },
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TownDao townDao();

    public abstract DistrictDao districtDao();

    public abstract CountryDao countryDao();

    public abstract LandmarkDao landmarkDao();

    public abstract ShopDao shopDao();

    public static volatile AppDatabase Instance;

    public static AppDatabase getInstance(Context context) {
        if (Instance == null) {
            synchronized (AppDatabase.class) {
                if (Instance == null) {
                    Instance = Room.databaseBuilder(
                            context,
                            AppDatabase.class,
                            "towns-db"
                    ).build();
                }
            }
        }
        return Instance;
    }
}

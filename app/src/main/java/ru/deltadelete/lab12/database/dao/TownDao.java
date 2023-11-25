package ru.deltadelete.lab12.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import ru.deltadelete.lab12.database.entities.Town;
import ru.deltadelete.lab12.database.entities.TownWithDistricts;

@Dao
public interface TownDao {
    @Query("select * from towns")
    List<Town> getAll();

    @Query("select * from towns where towns.country_id = :countryId")
    List<Town> getAllByCountry(int countryId);

    @Query("select * from towns where town_id in (:townIds)")
    List<Town> loadAllByIds(int[] townIds);

    @Query("select * from towns where town_name like :name")
    Town findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Town... towns);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Town town);

    @Update
    void update(Town town);

    @Delete
    void delete(Town... towns);

    @Transaction
    @Query("select * from towns t where t.town_id = :townId")
    TownWithDistricts getWithDistricts(int townId);

    @Transaction
    @Query("select * from towns")
    List<TownWithDistricts> getAllWithDistricts();
}

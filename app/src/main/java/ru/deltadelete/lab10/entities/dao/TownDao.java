package ru.deltadelete.lab10.entities.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import ru.deltadelete.lab10.entities.Town;
import ru.deltadelete.lab10.entities.TownWithDistricts;

@Dao
public interface TownDao {
    @Query("select * from towns")
    List<Town> getAll();

    @Query("select * from towns where town_id in (:townIds)")
    List<Town> loadAllByIds(int[] townIds);

    @Query("select * from towns where town_name like :name")
    Town findByName(String name);

    @Insert
    void insertAll(Town ...towns);

    @Delete
    void delete(Town ...towns);

    @Transaction
    @Query("select * from towns t where t.town_id = :townId")
    TownWithDistricts getWithDistricts(int townId);

    @Transaction
    @Query("select * from towns")
    List<TownWithDistricts> getAllWithDistricts();
}

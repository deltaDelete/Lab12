package ru.deltadelete.lab10.entities.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import ru.deltadelete.lab10.entities.*;

@Dao
public interface CountryDao {
    @Query("select * from countries")
    List<Country> getAll();

    @Query("select * from countries where country_id in (:ids)")
    List<Country> loadAllByIds(int[] ids);

    @Query("select * from countries where country_id like :name")
    Country findByName(String name);

    @Insert
    void insertAll(Country ...towns);

    @Delete
    void delete(Country...towns);

    @Transaction
    @Query("select * from countries c where c.country_id = :country_id")
    CountryWithTowns getWithTowns(int country_id);

    @Transaction
    @Query("select * from countries")
    List<CountryWithTowns> getAllWithTowns();
}

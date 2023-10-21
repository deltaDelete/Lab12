package ru.deltadelete.lab10.entities.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import ru.deltadelete.lab10.entities.District;
import ru.deltadelete.lab10.entities.DistrictWithThings;
import ru.deltadelete.lab10.entities.Town;
import ru.deltadelete.lab10.entities.TownWithDistricts;

@Dao
public interface DistrictDao {
    @Query("select * from districts")
    List<District> getAll();

    @Query("select * from districts where district_id in (:districtIds)")
    List<District> loadAllByIds(int[] districtIds);

    @Query("select * from districts where district_name like :name")
    District findByName(String name);

    @Insert
    void insertAll(District ...towns);

    @Delete
    void delete(District ...towns);

    @Transaction
    @Query("select * from districts d where d.district_id = :districtId")
    DistrictWithThings getWithDistricts(int districtId);

    @Transaction
    @Query("select * from districts")
    List<DistrictWithThings> getAllWithDistricts();
}

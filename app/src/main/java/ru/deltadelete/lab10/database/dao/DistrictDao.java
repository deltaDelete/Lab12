package ru.deltadelete.lab10.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import ru.deltadelete.lab10.database.entities.District;
import ru.deltadelete.lab10.database.entities.DistrictWithThings;

@Dao
public interface DistrictDao {
    @Query("select * from districts")
    List<District> getAll();

    @Query("select * from districts where district_id in (:districtIds)")
    List<District> loadAllByIds(int[] districtIds);

    @Query("select * from districts where district_name like :name")
    District findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(District... towns);

    @Update
    void update(District district);

    @Delete
    void delete(District... towns);

    @Transaction
    @Query("select * from districts d where d.district_id = :districtId")
    DistrictWithThings getWithDistricts(int districtId);

    @Transaction
    @Query("select * from districts")
    List<DistrictWithThings> getAllWithDistricts();
}

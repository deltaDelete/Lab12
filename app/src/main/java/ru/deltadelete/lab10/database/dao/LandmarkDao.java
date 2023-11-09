package ru.deltadelete.lab10.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.deltadelete.lab10.database.entities.Landmark;

@Dao
public interface LandmarkDao {
    @Query("select * from landmarks")
    List<Landmark> getAll();

    @Query("select * from landmarks where landmark_id in (:landmarkIds)")
    List<Landmark> loadAllByIds(int[] landmarkIds);

    @Query("select * from landmarks where landmark_name like :name")
    Landmark findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Landmark... landmarks);

    @Update
    void update(Landmark landmark);

    @Delete
    void delete(Landmark... landmarks);
}

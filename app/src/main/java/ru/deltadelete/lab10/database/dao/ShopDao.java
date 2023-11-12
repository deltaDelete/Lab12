package ru.deltadelete.lab10.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.deltadelete.lab10.database.entities.Shop;

@Dao
public interface ShopDao {
    @Query("select * from shops")
    List<Shop> getAll();

    @Query("select * from shops where shop_id in (:shopIds)")
    List<Shop> loadAllByIds(int[] shopIds);

    @Query("select * from shops where shop_name like :name")
    Shop findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Shop... shops);

    @Update
    void update(Shop shop);

    @Delete
    void delete(Shop... shops);
}

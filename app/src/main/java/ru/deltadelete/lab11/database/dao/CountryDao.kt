package ru.deltadelete.lab11.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ru.deltadelete.lab11.database.entities.Country
import ru.deltadelete.lab11.database.entities.CountryWithTowns

@Dao
interface CountryDao {
    @Query("select * from countries")
    fun all(): List<Country>

    @Query("select * from countries limit :take")
    fun take(take: Int): List<Country>

    @Query("select * from countries where country_id in (:ids)")
    fun loadAllByIds(ids: IntArray): List<Country>

    @Query("select * from countries where country_id like :name")
    fun findByName(name: String): Country?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg towns: Country)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(country: Country): Long

    @Update
    fun update(country: Country)

    @Delete
    fun delete(vararg towns: Country)

    @Transaction
    @Query("select * from countries c where c.country_id = :countryId")
    fun getWithTowns(countryId: Int): CountryWithTowns?

    @Query("select * from countries")
    @Transaction
    fun allWithTowns(): List<CountryWithTowns>
}
package ru.deltadelete.lab10.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.deltadelete.lab10.database.entities.Country
import ru.deltadelete.lab10.database.entities.CountryWithTowns

@Dao
interface CountryDao {
    // TODO разобраться с корутинами
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

    @Update
    fun update(country: Country)

    @Delete
    fun delete(towns: List<Country>)

    @Transaction
    @Query("select * from countries c where c.country_id = :countryId")
    fun getWithTowns(countryId: Int): CountryWithTowns?

    @Query("select * from countries")
    @Transaction
    fun allWithTowns(): List<CountryWithTowns>
}
package ru.deltadelete.lab10.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.deltadelete.lab10.database.entities.Country
import ru.deltadelete.lab10.database.entities.CountryWithTowns

@Dao
interface CountryDao {
    // TODO разобраться с корутинами
    @Query("select * from countries")
    suspend fun all(): List<Country>

    @Query("select * from countries limit :take")
    suspend fun take(take: Int): List<Country>

    @Query("select * from countries where country_id in (:ids)")
    suspend fun loadAllByIds(ids: IntArray): List<Country>

    @Query("select * from countries where country_id like :name")
    suspend fun findByName(name: String): Country?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg towns: Country)

    @Update
    suspend fun update(country: Country)

    @Delete
    suspend fun delete(towns: List<Country>)

    @Transaction
    @Query("select * from countries c where c.country_id = :countryId")
    suspend fun getWithTowns(countryId: Int): CountryWithTowns?

    @Query("select * from countries")
    @Transaction
    suspend fun allWithTowns(): List<CountryWithTowns>
}
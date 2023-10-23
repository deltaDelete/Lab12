package ru.deltadelete.lab10.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class Country (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "country_id")
    val id: Int = 0,

    @ColumnInfo(name = "country_name")
    val name: String,

    @ColumnInfo(name = "country_code")
    val code: String,
)
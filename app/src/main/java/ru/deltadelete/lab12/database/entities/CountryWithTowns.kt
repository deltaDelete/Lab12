package ru.deltadelete.lab12.database.entities

import androidx.room.Embedded
import androidx.room.Relation

public data class CountryWithTowns (
    @Embedded
    var country: Country? = null,

    @Relation(parentColumn = "country_id", entityColumn = "country_id")
    var towns: List<Town>? = null,
)

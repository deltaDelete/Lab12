package ru.deltadelete.lab11.database.entities

import androidx.room.Embedded
import androidx.room.Relation

public data class TownWithDistricts(

    @Embedded
    var town: Town? = null,

    @Relation(parentColumn = "town_id", entityColumn = "town_id")
    var districts: List<District>? = null,
)

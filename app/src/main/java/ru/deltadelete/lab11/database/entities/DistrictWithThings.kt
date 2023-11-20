package ru.deltadelete.lab11.database.entities

import androidx.room.Embedded
import androidx.room.Relation

public data class DistrictWithThings (

    @Embedded
    var district: District? = null,

    @Relation(parentColumn = "district_id", entityColumn = "district_id")
    var landmarks: List<Landmark>? = null,

    @Relation(parentColumn = "district_id", entityColumn = "district_id")
    var shops: List<Shop>? = null,
)

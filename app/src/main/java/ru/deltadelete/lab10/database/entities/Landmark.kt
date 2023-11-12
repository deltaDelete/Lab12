package ru.deltadelete.lab10.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ru.deltadelete.lab10.database.entities.District

@Entity(
    tableName = "landmarks",
    foreignKeys = [ForeignKey(
        entity = District::class,
        parentColumns = ["district_id"],
        childColumns = ["district_id"],
        onDelete = CASCADE
    )]
)
data class Landmark(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "landmark_id")
    val id : Int = 0,

    @ColumnInfo(name = "landmark_name")
    val name: String?,

    @ColumnInfo(name = "district_id", index = true)
    val districtId: Int
)
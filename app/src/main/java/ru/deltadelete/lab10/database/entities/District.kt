package ru.deltadelete.lab10.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ru.deltadelete.lab10.database.entities.Town

@Entity(
    tableName = "districts",
    foreignKeys = [ForeignKey(
        entity = Town::class,
        parentColumns = ["town_id"],
        childColumns = ["town_id"],
        onDelete = CASCADE
    )]
)
data class District (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "district_id")
    val id : Int = 0,

    @ColumnInfo(name = "district_name")
    val name: String,

    @ColumnInfo(name = "town_id", index = true)
    val townId: Int,
)
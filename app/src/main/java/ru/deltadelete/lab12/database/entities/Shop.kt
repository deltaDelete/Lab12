package ru.deltadelete.lab12.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "shops",
    foreignKeys = [ForeignKey(
        entity = District::class,
        parentColumns = ["district_id"],
        childColumns = ["district_id"],
        onDelete = CASCADE
    )]
)
data class Shop(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "shop_id")
    val id: Int = 0,

    @ColumnInfo(name = "shop_name")
    val name: String,

    @ColumnInfo(name = "district_id", index = true)
    val districtId: Int,
)
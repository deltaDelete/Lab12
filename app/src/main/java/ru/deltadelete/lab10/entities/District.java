package ru.deltadelete.lab10.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "districts",
        foreignKeys = {
                @ForeignKey(
                        entity = Town.class,
                        parentColumns = {"town_id"},
                        childColumns = {"town_id"},
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class District {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "district_id")
    public int id;

    @ColumnInfo(name = "district_name")
    public String name;

    @ColumnInfo(name = "town_id")
    public int townId;
}

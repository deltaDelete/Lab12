package ru.deltadelete.lab10.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "towns",
        foreignKeys = {
                @ForeignKey(
                        entity = Country.class,
                        parentColumns = {"country_id"},
                        childColumns = {"country_id"},
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Town {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "town_id")
    public int id;

    @ColumnInfo(name = "town_name")
    public String name;

    @ColumnInfo()
    public String description;

    @ColumnInfo(name = "country_id")
    public int countryId;
}

package ru.deltadelete.lab10.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "countries")
public class Country {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "country_id")
    public int id;

    @ColumnInfo(name = "country_name")
    public String name;

    @ColumnInfo(name = "country_code")
    public String code;
}

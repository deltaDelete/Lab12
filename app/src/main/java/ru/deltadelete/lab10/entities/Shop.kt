package ru.deltadelete.lab10.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "landmarks",
        foreignKeys = {
                @ForeignKey(
                        entity = District.class,
                        parentColumns = {"district_id"},
                        childColumns = {"district_id"},
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Shop {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "shop_id")
    public int id;
    @ColumnInfo(name = "shop_name")
    public String name;
    @ColumnInfo(name = "district_id")
    public int districtId;
}

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
public class Landmark {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "landmark_id")
    public int id;

    @ColumnInfo(name = "landmark_name")
    public String name;

    @ColumnInfo(name = "district_id")
    public int districtId;
}

package ru.deltadelete.lab10.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DistrictWithThings {
    @Embedded
    public District district;
    @Relation(parentColumn = "district_id", entityColumn = "district_id")
    public List<Landmark> landmarks;
    @Relation(parentColumn = "district_id", entityColumn = "district_id")
    public List<Shop> shops;
}

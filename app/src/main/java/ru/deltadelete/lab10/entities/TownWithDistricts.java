package ru.deltadelete.lab10.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TownWithDistricts {
    @Embedded
    public Town town;
    @Relation(parentColumn = "town_id", entityColumn = "town_id")
    public List<District> districts;
}

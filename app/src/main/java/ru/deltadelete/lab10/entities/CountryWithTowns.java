package ru.deltadelete.lab10.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CountryWithTowns {
    @Embedded public Country country;
    @Relation(parentColumn = "country_id", entityColumn = "country_id")
    public List<Town> towns;
}

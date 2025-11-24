package com.example.projekt_jo_eu.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "countries")
public class Country {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String capital;
    public String description;


    public boolean isFavorite;


    public Country(String name, String capital, String description) {
        this.name = name;
        this.capital = capital;
        this.description = description;
        this.isFavorite = false;
    }


    public Country() {
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCapital() { return capital; }
    public void setCapital(String capital) { this.capital = capital; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}
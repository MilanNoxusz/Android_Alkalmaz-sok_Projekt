package com.example.projekt_jo_eu.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CountryDao {

    @Insert
    void insert(Country country);

    @Insert
    void insertAll(List<Country> countries);

    @Update
    void update(Country country);


    @Query("SELECT * FROM countries ORDER BY name ASC")
    List<Country> getAllCountries();


    @Query("SELECT * FROM countries WHERE isFavorite = 1 ORDER BY name ASC")
    List<Country> getFavoriteCountries();


    @Query("SELECT * FROM countries WHERE name LIKE :searchQuery")
    List<Country> searchCountries(String searchQuery);


    @Query("SELECT * FROM countries WHERE id = :id")
    Country getCountryById(int id);
}
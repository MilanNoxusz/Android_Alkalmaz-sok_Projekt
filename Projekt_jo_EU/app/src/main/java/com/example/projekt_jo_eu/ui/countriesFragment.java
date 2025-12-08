package com.example.projekt_jo_eu.ui;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projekt_jo_eu.MyAdapter;
import com.example.projekt_jo_eu.R;
import com.example.projekt_jo_eu.data.Country;
import com.example.projekt_jo_eu.data.CountryDatabase;

import java.util.ArrayList;
import java.util.List;

public class countriesFragment extends Fragment {

    public countriesFragment() {
        // Required empty public constructor
    }

    public static countriesFragment newInstance(String param1, String param2) {
        countriesFragment fragment = new countriesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Layout betöltése
        return inflater.inflate(R.layout.countries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.countries_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Üres adapter beállítása kezdetben, hogy ne legyen üres a képernyő
        MyAdapter adapter = new MyAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        android.widget.Button backBtn = view.findViewById(R.id.back_button_countries);
        backBtn.setOnClickListener(v -> {
            androidx.navigation.Navigation.findNavController(view).popBackStack();
        });

        // --- LIVEDATA MEGOLDÁS ---

        // 1. Adatbázis példány
        CountryDatabase db = CountryDatabase.getDatabase(getContext());

        // 2. Feliratkozás a LiveData-ra
        // A "getViewLifecycleOwner()" biztosítja, hogy csak akkor fusson, ha a nézet aktív
        db.countryDao().getAllCountries().observe(getViewLifecycleOwner(), countryList -> {

            // Ez a kódblokk automatikusan lefut MINDEN alkalommal,
            // amikor az adatbázisban változik valami (pl. betöltődnek az adatok).

            if (countryList != null && !countryList.isEmpty()) {
                List<String> countryNames = new ArrayList<>();
                for (Country country : countryList) {
                    countryNames.add(country.getName());
                }

                // Adapter frissítése az új adatokkal
                // (Mivel a MyAdapter-ben nincs külön update metódus, újat hozunk létre)
                MyAdapter newAdapter = new MyAdapter(countryNames);
                recyclerView.setAdapter(newAdapter);
            }
        });
    }
}
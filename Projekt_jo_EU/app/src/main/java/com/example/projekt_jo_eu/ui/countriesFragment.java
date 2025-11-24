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

        // 1. RecyclerView megkeresése a layoutban
        RecyclerView recyclerView = view.findViewById(R.id.countries_recyclerView);

        // 2. LayoutManager beállítása (ez rendezi listába az elemeket)
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Vissza gomb kezelése
        android.widget.Button backBtn = view.findViewById(R.id.back_button_countries);
        backBtn.setOnClickListener(v -> {
            androidx.navigation.Navigation.findNavController(view).popBackStack();
        });

        // 3. Adatbázis elérése és adatok betöltése
        new Thread(() -> {

            CountryDatabase db = CountryDatabase.getDatabase(getContext());


            List<Country> countryList = db.countryDao().getAllCountries();



            List<String> countryNames = new ArrayList<>();
            if (countryList != null) {
                for (Country country : countryList) {
                    countryNames.add(country.getName());
                }
            }


            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    // Adapter létrehozása a nevek listájával
                    MyAdapter adapter = new MyAdapter(countryNames);
                    // Adapter csatlakoztatása a RecyclerView-hoz
                    recyclerView.setAdapter(adapter);
                });
            }
        }).start();
    }
}
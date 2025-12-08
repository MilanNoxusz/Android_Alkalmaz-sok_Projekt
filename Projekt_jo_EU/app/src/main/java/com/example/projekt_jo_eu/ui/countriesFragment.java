package com.example.projekt_jo_eu.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projekt_jo_eu.MyAdapter;
import com.example.projekt_jo_eu.R;
import com.example.projekt_jo_eu.data.CountryDatabase;

import java.util.ArrayList;

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
        return inflater.inflate(R.layout.countries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.countries_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Vissza gomb
        android.widget.Button backBtn = view.findViewById(R.id.back_button_countries);
        backBtn.setOnClickListener(v -> {
            androidx.navigation.Navigation.findNavController(view).popBackStack();
        });

        // Adatbázis elérése és lista betöltése LiveData-val
        CountryDatabase db = CountryDatabase.getDatabase(getContext());

        db.countryDao().getAllCountries().observe(getViewLifecycleOwner(), countryList -> {
            if (countryList != null) {
                // Adapter létrehozása a kattintás figyelővel
                MyAdapter adapter = new MyAdapter(countryList, country -> {
                    // KATTINTÁS KEZELÉSE:
                    // 1. Megkeressük a NavController-t
                    NavController navController = Navigation.findNavController(view);

                    // 2. Csomagba (Bundle) tesszük az ID-t
                    Bundle bundle = new Bundle();
                    bundle.putInt("countryId", country.getId());

                    // 3. Átnavigálunk a részletes oldalra az adatokkal
                    navController.navigate(R.id.countryDetailFragment, bundle);
                });

                recyclerView.setAdapter(adapter);
            }
        });
    }
}
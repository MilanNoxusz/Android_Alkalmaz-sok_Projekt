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
import com.example.projekt_jo_eu.data.Country;
import com.example.projekt_jo_eu.data.CountryDatabase;

public class countriesFragment extends Fragment {

    public countriesFragment() {

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

        // Adatbázis elérése
        CountryDatabase db = CountryDatabase.getDatabase(getContext());

        // Lista figyelése
        db.countryDao().getAllCountries().observe(getViewLifecycleOwner(), countryList -> {
            if (countryList != null) {
                // Adapter létrehozása a javított módszerrel
                MyAdapter adapter = new MyAdapter(countryList, new MyAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(Country country) {
                        // Navigáció a részletekre
                        NavController navController = Navigation.findNavController(view);
                        Bundle bundle = new Bundle();
                        bundle.putInt("countryId", country.getId());
                        navController.navigate(R.id.countryDetailFragment, bundle);
                    }

                    @Override
                    public void onFavoriteClick(Country country) {
                        // Kedvenc állapot mentése
                        boolean newState = !country.isFavorite();
                        country.setFavorite(newState);

                        CountryDatabase.databaseWriteExecutor.execute(() -> {
                            db.countryDao().update(country);
                        });
                    }
                });

                recyclerView.setAdapter(adapter);
            }
        });
    }
}
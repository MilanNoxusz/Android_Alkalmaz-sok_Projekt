package com.example.projekt_jo_eu.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.projekt_jo_eu.R;
import com.example.projekt_jo_eu.data.Country;
import com.example.projekt_jo_eu.data.CountryDao;
import com.example.projekt_jo_eu.data.CountryDatabase;

import java.util.List;


public class statisticsFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    // Adatbázis eléréshez szükséges változó
    private CountryDao countryDao;

    // UI elemek változói
    private TextView totalCountriesValue;
    private TextView favoriteCountriesValue;
    private ProgressBar favoritesProgress;

    public statisticsFragment() {
        // Required empty public constructor
    }

    public static statisticsFragment newInstance(String param1, String param2) {
        statisticsFragment fragment = new statisticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Adatbázis inicializálása
        CountryDatabase db = CountryDatabase.getDatabase(requireContext());
        countryDao = db.countryDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.statistics_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // UI elemek megkeresése
        totalCountriesValue = view.findViewById(R.id.total_countries_value);
        favoriteCountriesValue = view.findViewById(R.id.favorite_countries_value);
        favoritesProgress = view.findViewById(R.id.favorites_progress);
        Button backBtn = view.findViewById(R.id.back_button_statistics);

        // Vissza gomb működése
        backBtn.setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });

        // 1. Összes ország lekérdezése és megjelenítése
        countryDao.getAllCountries().observe(getViewLifecycleOwner(), new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
                if (countries != null) {
                    int count = countries.size(); // Lista mérete = országok száma
                    totalCountriesValue.setText(String.valueOf(count));

                    // A progress bar maximum értékét beállítjuk az összes ország számára
                    favoritesProgress.setMax(count);
                }
            }
        });

        // 2. Kedvenc országok lekérdezése és megjelenítése
        countryDao.getFavoriteCountries().observe(getViewLifecycleOwner(), new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> favoriteCountries) {
                if (favoriteCountries != null) {
                    int favCount = favoriteCountries.size(); // Lista mérete = kedvencek száma
                    favoriteCountriesValue.setText(String.valueOf(favCount));

                    // A progress bar aktuális értékét beállítjuk a kedvencek számára
                    favoritesProgress.setProgress(favCount);
                }
            }
        });
    }
}
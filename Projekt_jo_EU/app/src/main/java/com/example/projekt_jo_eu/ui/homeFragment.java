package com.example.projekt_jo_eu.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity; // Import hozzáadva
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projekt_jo_eu.R;

public class homeFragment extends Fragment {

    public homeFragment() {
        // Required empty public constructor
    }

    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        Button orszagokBtn = view.findViewById(R.id.orszagok_btn);
        Button favouriteBtn = view.findViewById(R.id.favourite_btn);
        Button statisticsBtn = view.findViewById(R.id.statistict_btn);
        Button randomBtn = view.findViewById(R.id.random_btn);

        // 1. Országok gomb -> CountriesFragment
        orszagokBtn.setOnClickListener(v -> {
            navController.navigate(R.id.countriesFragment);
        });

        // 2. Kedvencek gomb -> favoriteFragment
        favouriteBtn.setOnClickListener(v -> {
            navController.navigate(R.id.favoriteFragment);
        });

        // 3. Statisztika gomb -> statisticsFragment
        statisticsBtn.setOnClickListener(v -> {
            navController.navigate(R.id.statisticsFragment);
        });

        // 4. Random gomb -> randomFragment
        randomBtn.setOnClickListener(v -> {
            navController.navigate(R.id.randomFragment);
        });
    }

    // Amikor a fragment láthatóvá válik (Kezdőlap), elrejtjük a felső sávot
    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null && ((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
    }

    // Amikor elnavigálunk a fragmentről (pl. Stop), visszakapcsoljuk a sávot,
    // hogy a többi oldalon (pl. Országok) látszódjon.
    @Override
    public void onStop() {
        super.onStop();
        if (getActivity() != null && ((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }
    }
}
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

public class favoriteFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public favoriteFragment() {
        // Required empty public constructor
    }

    public static favoriteFragment newInstance(String param1, String param2) {
        favoriteFragment fragment = new favoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.favorite, container, false);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        android.widget.Button backBtn = view.findViewById(R.id.back_btn_favorite);
        backBtn.setOnClickListener(v -> {
            androidx.navigation.Navigation.findNavController(view).popBackStack();
        });
        // RecyclerView beállítása
        RecyclerView recyclerView = view.findViewById(R.id.favorites_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Adatbázis elérése
        CountryDatabase db = CountryDatabase.getDatabase(getContext());

        // Kedvencek lekérdezése és megjelenítése
        db.countryDao().getFavoriteCountries().observe(getViewLifecycleOwner(), countryList -> {
            if (countryList != null) {
                MyAdapter adapter = new MyAdapter(countryList, new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Country country) {
                        // Részletek megnyitása
                        NavController navController = Navigation.findNavController(view);
                        Bundle bundle = new Bundle();
                        bundle.putInt("countryId", country.getId());
                        navController.navigate(R.id.countryDetailFragment, bundle);
                    }

                    @Override
                    public void onFavoriteClick(Country country) {
                        // Kedvenc állapot törlése és mentése
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
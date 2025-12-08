package com.example.projekt_jo_eu.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.projekt_jo_eu.R;
import com.example.projekt_jo_eu.data.Country;
import com.example.projekt_jo_eu.data.CountryDatabase;

public class countryDetailFragment extends Fragment {

    public countryDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.country_detail_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vissza gomb
        android.widget.Button backBtn = view.findViewById(R.id.back_button_countriesdetail);
        backBtn.setOnClickListener(v -> {
            androidx.navigation.Navigation.findNavController(view).popBackStack();
        });

        // UI elemek megkeresése
        TextView nameTextView = view.findViewById(R.id.country_name_detail_text);
        TextView capitalTextView = view.findViewById(R.id.country_capital_text);
        TextView descriptionTextView = view.findViewById(R.id.information_txtView);
        // Megjegyzés: A jelenlegi adatbázisban nincs pénznem adat, így azt most kihagyjuk vagy fix szöveget írunk.
        TextView currencyTextView = view.findViewById(R.id.country_currency_text);

        // ID kinyerése az átadott argumentumokból
        if (getArguments() != null) {
            int countryId = getArguments().getInt("countryId", -1);

            if (countryId != -1) {
                // Adatok betöltése háttérszálon az ID alapján
                CountryDatabase.databaseWriteExecutor.execute(() -> {
                    CountryDatabase db = CountryDatabase.getDatabase(getContext());
                    Country country = db.countryDao().getCountryById(countryId);

                    // Visszatérés a fő szálra a megjelenítéshez
                    if (getActivity() != null && country != null) {
                        getActivity().runOnUiThread(() -> {
                            nameTextView.setText(country.getName());
                            capitalTextView.setText(country.getCapital());
                            descriptionTextView.setText(country.getDescription());
                            currencyTextView.setText("-"); // Nincs adat az adatbázisban
                        });
                    }
                });
            }
        }
    }
}
package com.example.projekt_jo_eu.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.projekt_jo_eu.R;
import com.example.projekt_jo_eu.data.Country;
import com.example.projekt_jo_eu.data.CountryDatabase;

public class randomFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public randomFragment() {
    }

    public static randomFragment newInstance(String param1, String param2) {
        randomFragment fragment = new randomFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.random_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button backBtn = view.findViewById(R.id.back_button_random);
        backBtn.setOnClickListener(v -> {
            Navigation.findNavController(view).popBackStack();
        });

        Button randomBtn = view.findViewById(R.id.button2);
        TextView countryNameTv = view.findViewById(R.id.country_name_textview);
        TextView capitalTv = view.findViewById(R.id.country_capital_textview);

        randomBtn.setOnClickListener(v -> {
            CountryDatabase db = CountryDatabase.getDatabase(getContext());
            CountryDatabase.databaseWriteExecutor.execute(() -> {
                Country randomCountry = db.countryDao().getRandomCountry();
                if (getActivity() != null && randomCountry != null) {
                    getActivity().runOnUiThread(() -> {
                        countryNameTv.setText(randomCountry.getName());
                        capitalTv.setText(randomCountry.getCapital());
                    });
                }
            });
        });
    }
}
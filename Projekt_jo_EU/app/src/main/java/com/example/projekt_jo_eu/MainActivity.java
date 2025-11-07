package com.example.projekt_jo_eu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fooldal);

        Button orszagokBtn = findViewById(R.id.orszagok_btn);
        Button favouriteBtn = findViewById(R.id.favourite_btn);
        Button statistictBtn = findViewById(R.id.statistict_btn);
        Button randomBtn = findViewById(R.id.random_btn);

        orszagokBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CountriesActivity.class);
                startActivity(intent);
            }
        });

        favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });

        statistictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });

        randomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RandomActivity.class);
                startActivity(intent);
            }
        });
    }
}

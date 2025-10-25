package com.example.projekt_jo_eu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<String> itemList;
    private SearchView searchView;
    private ChipGroup sortingChipGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- Toolbar beállítása ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // --- Adatok előkészítése ---
        setupData();

        // --- RecyclerView beállítása ---
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(itemList);
        recyclerView.setAdapter(adapter);

        // --- SearchView (szűrés) beállítása ---
        searchView = findViewById(R.id.searchView);
        setupSearchView();

        // --- ChipGroup (rendezés) beállítása ---
        sortingChipGroup = findViewById(R.id.sorting_chip_group);
        setupSorting();
    }

    private void setupData() {
        // Dummy adatok létrehozása a listához
        itemList = new ArrayList<>();
        itemList.add("Android");
        itemList.add("Kotlin");
        itemList.add("Java");
        itemList.add("XML");
        itemList.add("Fejlesztés");
        itemList.add("ConstraintLayout");
        itemList.add("RecyclerView");
        itemList.add("Adapter");
        itemList.add("Material Design");
        itemList.add("Gradle");
        itemList.add("ViewModel");
        itemList.add("LiveData");
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Akkor hívódik meg, amikor a felhasználó a "keresés" gombra nyom
                adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Akkor hívódik meg, amikor a keresőmező tartalma megváltozik
                adapter.filter(newText);
                return true;
            }
        });
    }

    private void setupSorting() {
        sortingChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.sort_asc_chip) {
                // Növekvő rendezés
                sortList(true);
            } else if (checkedId == R.id.sort_desc_chip) {
                // Csökkenő rendezés
                sortList(false);
            }
        });
    }

    private void sortList(boolean ascending) {
        // A rendezést az adapter eredeti listáján végezzük
        if (ascending) {
            // Növekvő sorrend (A-Z)
            Collections.sort(adapter.originalItems, Comparator.naturalOrder());
        } else {
            // Csökkenő sorrend (Z-A)
            Collections.sort(adapter.originalItems, Comparator.reverseOrder());
        }

        // A szűrés újbóli alkalmazása a rendezett listára
        // A searchView.getQuery() visszaadja az aktuális keresési kifejezést
        String currentQuery = searchView.getQuery().toString();
        adapter.filter(currentQuery);
    }
}


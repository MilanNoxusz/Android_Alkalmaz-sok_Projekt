package com.example.projekt_jo_eu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fooldal);

        // Toolbar beállítása
        Toolbar mainToolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);

        // Címsor beállítása
        setTitle("Navigation app");

        // Navigation beállítása
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        // Top level destinations beállítása
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment

        ).build();

        NavigationUI.setupActionBarWithNavController(this,
                navController, appBarConfiguration);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /*if(item.getItemId() == R.id.homeFragment) {
            showMessage("Kezdőlap");
        } else if(item.getItemId() == R.id.profileFragment) {
            showMessage("Profil");
        } else if(item.getItemId() == R.id.settingsFragment) {
            showMessage("Beállítások");
        }

        return super.onOptionsItemSelected(item);*/

        return NavigationUI.onNavDestinationSelected(item, navController);
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_SHORT).show();
    }
}
package com.example.projekt_jo_eu.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Country.class}, version = 4, exportSchema = false)
public abstract class CountryDatabase extends RoomDatabase {

    public abstract CountryDao countryDao();

    private static volatile CountryDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CountryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CountryDatabase.class) {
                if (INSTANCE == null) {
                    // Itt átadjuk a context-et a callbacknek, hogy elérje a fájlt
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CountryDatabase.class, "country_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(new RoomDatabaseCallback(context))
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static class RoomDatabaseCallback extends RoomDatabase.Callback {
        private final Context context;

        RoomDatabaseCallback(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {

                try {
                    InputStream inputStream = context.getAssets().open("countries_with_capitals_and_desc.sql");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    int count = 0;

                    db.beginTransaction();
                    try {
                        while ((line = reader.readLine()) != null) {
                            // Csak a nem üres és nem CREATE parancsokat futtatjuk
                            if (!line.trim().isEmpty() && !line.startsWith("CREATE")) {
                                try {
                                    db.execSQL(line);
                                    count++;
                                } catch (Exception e) {
                                    Log.e("Adatbazis", "Hiba ennél a sornál: " + line, e);
                                }
                            }
                        }
                        db.setTransactionSuccessful();
                        Log.d("Adatbazis", "SIKER! " + count + " ország betöltve az adatbázisba.");
                    } finally {
                        db.endTransaction();
                        reader.close();
                    }
                } catch (IOException e) {
                    Log.e("Adatbazis", "Hiba az SQL fájl megnyitásakor!", e);
                }
            });
        }
    }
}
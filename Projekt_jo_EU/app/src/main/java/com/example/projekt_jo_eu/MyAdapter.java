package com.example.projekt_jo_eu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projekt_jo_eu.data.Country;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public List<Country> originalItems;
    private List<Country> filteredItems;
    private final OnItemClickListener listener;

    // Itt definiáljuk a két eseményt: sima kattintás és kedvenc kattintás
    public interface OnItemClickListener {
        void onItemClick(Country country);
        void onFavoriteClick(Country country);
    }

    public MyAdapter(List<Country> items, OnItemClickListener listener) {
        this.originalItems = new ArrayList<>(items);
        this.filteredItems = new ArrayList<>(items);
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Country country = filteredItems.get(position);
        holder.bind(country, listener);
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView countryName;
        public ImageView favoriteIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.country_name_text);
            // Fontos: a layout xml-ben ennek az ID-nak léteznie kell!
            favoriteIcon = itemView.findViewById(R.id.favorite_icon);
        }

        public void bind(final Country country, final OnItemClickListener listener) {
            countryName.setText(country.getName());

            // Ikon beállítása az aktuális állapot szerint
            if (country.isFavorite()) {
                favoriteIcon.setImageResource(R.drawable.ic_star);
            } else {
                favoriteIcon.setImageResource(R.drawable.ic_star_border);
            }

            // Teljes sorra kattintás (Részletek)
            itemView.setOnClickListener(v -> listener.onItemClick(country));

            // Csak a csillagra kattintás (Kedvenc)
            favoriteIcon.setOnClickListener(v -> {
                // Azonnali vizuális visszajelzés
                if (country.isFavorite()) {
                    favoriteIcon.setImageResource(R.drawable.ic_star_border);
                } else {
                    favoriteIcon.setImageResource(R.drawable.ic_star);
                }
                listener.onFavoriteClick(country);
            });
        }
    }

    // Kereső funkció
    public void filter(String query) {
        filteredItems.clear();
        if (query.isEmpty()) {
            filteredItems.addAll(originalItems);
        } else {
            query = query.toLowerCase();
            for (Country item : originalItems) {
                if (item.getName().toLowerCase().contains(query)) {
                    filteredItems.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
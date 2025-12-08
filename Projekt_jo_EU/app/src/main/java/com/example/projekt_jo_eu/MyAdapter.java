package com.example.projekt_jo_eu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projekt_jo_eu.data.Country;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    // Most már Country objektumokat tárolunk String helyett
    public List<Country> originalItems;
    private List<Country> filteredItems;
    private final OnItemClickListener listener;

    // Interfész a kattintás kezeléséhez
    public interface OnItemClickListener {
        void onItemClick(Country country);
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.country_name_text);
        }

        public void bind(final Country country, final OnItemClickListener listener) {
            countryName.setText(country.getName());

            // Kattintás esemény beállítása az elemre
            itemView.setOnClickListener(v -> listener.onItemClick(country));
        }
    }

    // Keresés funkció frissítése az új típushoz
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
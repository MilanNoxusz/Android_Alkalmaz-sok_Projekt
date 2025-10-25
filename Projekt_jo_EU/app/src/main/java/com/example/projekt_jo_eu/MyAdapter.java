package com.example.projekt_jo_eu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    // A lista, amely az eredeti, teljes adatokat tartalmazza
    public List<String> originalItems;
    // A lista, amely a szűrt és rendezett adatokat tartalmazza, ez jelenik meg
    private List<String> filteredItems;

    // Konstruktor, amely fogadja a kezdeti adatlistát
    public MyAdapter(List<String> items) {
        this.originalItems = new ArrayList<>(items);
        this.filteredItems = new ArrayList<>(items);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Létrehozzuk a nézetet az egyszerű lista elem XML-ből
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Beállítjuk az adatot az adott pozícióban lévő elemre
        String item = filteredItems.get(position);
        holder.textView.setText(item);
    }

    @Override
    public int getItemCount() {
        // Visszaadjuk a megjelenítendő (szűrt) elemek számát
        return filteredItems.size();
    }

    // A ViewHolder osztály, amely a lista egy elemének nézetét tárolja
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    // Metódus a lista szűrésére a keresési kifejezés alapján
    public void filter(String query) {
        filteredItems.clear();
        if (query.isEmpty()) {
            // Ha a keresés üres, a teljes listát megjelenítjük
            filteredItems.addAll(originalItems);
        } else {
            // Egyébként végigmegyünk az eredeti listán és hozzáadjuk a találatokat
            query = query.toLowerCase();
            for (String item : originalItems) {
                if (item.toLowerCase().contains(query)) {
                    filteredItems.add(item);
                }
            }
        }
        // Értesítjük az adaptert, hogy az adatok megváltoztak, és frissítenie kell a nézetet
        notifyDataSetChanged();
    }
}

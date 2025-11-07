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


    public List<String> originalItems;

    private List<String> filteredItems;


    public MyAdapter(List<String> items) {
        this.originalItems = new ArrayList<>(items);
        this.filteredItems = new ArrayList<>(items);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String item = filteredItems.get(position);
        holder.textView.setText(item);
    }

    @Override
    public int getItemCount() {

        return filteredItems.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }


    public void filter(String query) {
        filteredItems.clear();
        if (query.isEmpty()) {

            filteredItems.addAll(originalItems);
        } else {

            query = query.toLowerCase();
            for (String item : originalItems) {
                if (item.toLowerCase().contains(query)) {
                    filteredItems.add(item);
                }
            }
        }

        notifyDataSetChanged();
    }
}

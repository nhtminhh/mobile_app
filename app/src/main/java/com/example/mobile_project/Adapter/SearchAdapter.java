package com.example.mobile_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_project.Hiking;
import com.example.mobile_project.MainActivity;
import com.example.mobile_project.Observation;
import com.example.mobile_project.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private final Context context;

    public List<Hiking> getHikings() {
        return hikings;
    }
    private MainActivity mainActivity;

    public void setHikings(List<Hiking> hikings) {
        if (hikings == null)
            return;
        this.hikings = hikings;
    }
    private List<Hiking> hikings = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id;
        public TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = itemView.findViewById(R.id.search_hiking_id);
            this.name = itemView.findViewById(R.id.search_name);
        }
    }

    public SearchAdapter(Context context, List<Hiking> hikingArrayList, MainActivity mainActivity) {
        this.context = context;
        this.hikings = hikingArrayList;
        this.mainActivity = mainActivity;

    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
        final Hiking hiking = hikings.get(position);

        holder.id.setText(String.valueOf(hiking.getId()));
        holder.name.setText(hiking.getName());

        // Set onclick listener

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.getHikingArrayList().clear();
                mainActivity.getHikingArrayList().addAll(hikings);
                mainActivity.addAnEditHiking(true, hiking, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hikings.size();
    }

}

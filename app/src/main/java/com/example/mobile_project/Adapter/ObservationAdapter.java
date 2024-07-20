package com.example.mobile_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_project.MainActivity;
import com.example.mobile_project.Observation;
import com.example.mobile_project.R;

import java.util.ArrayList;
import java.util.List;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.MyViewHolder> {
    private Context context;

    public void addObservationToList(Observation observation) {
        if (observationList == null)
            observationList = new ArrayList<>();
        observationList.add(observation);
    }

    public List<Observation> getObservationList() {
        return observationList;
    }

    private List<Observation> observationList;
    private MainActivity mainActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView observation;
        public TextView hiking_id;

        public TextView time;
        public TextView comment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.hiking_id = itemView.findViewById(R.id.hiking_id_obs);
            this.observation = itemView.findViewById(R.id.observation_name);
            this.time = itemView.findViewById(R.id.observation_time);
            this.comment = itemView.findViewById(R.id.observation_comment);
        }
    }
    public ObservationAdapter(Context context, ArrayList<Observation> observationArrayList, MainActivity mainActivity) {
        this.context = context;
        this.observationList = observationArrayList;
        this.mainActivity = mainActivity;
    }

    public ObservationAdapter(Context context, MainActivity mainActivity) {
        this.context = context;
        this.observationList = new ArrayList<>();
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ObservationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.observation_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationAdapter.MyViewHolder holder, int position) {
        final Observation observation = observationList.get(position);
        holder.hiking_id.setText(String.valueOf(observation.getHikingId()));
        holder.observation.setText(observation.getObservation());
        holder.time.setText(observation.getTime());
        holder.comment.setText(observation.getComments());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xu ly goi den screen khac
                mainActivity.addAnEditObservation(true, observation, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return observationList.size();
    }
}

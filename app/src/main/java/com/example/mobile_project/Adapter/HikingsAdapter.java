package com.example.mobile_project.Adapter;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.Button;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_project.DatabaseHelper;
import com.example.mobile_project.MainActivity;
import com.example.mobile_project.Observation;
import com.example.mobile_project.R;
import com.example.mobile_project.Hiking;

import java.util.ArrayList;
import java.util.List;

public class HikingsAdapter extends RecyclerView.Adapter<HikingsAdapter.MyViewHolder> {

    EditText obserObservation, obserTime, obserComment, obserHikeId;
    private Context context;
    private List<Hiking> contactList;

    private ArrayList<Observation> observationArrayList;
    private MainActivity mainActivity;
    private RecyclerView recyclerViewObs;
    private View addObsView;

    private ObservationAdapter observationAdapter;
    private DatabaseHelper db;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView id;

        public TextView name;
        public TextView location;
        public TextView date;
        public TextView length;
        public TextView trail;
        public TextView parking;
        public TextView level;
        public TextView weather;
        public TextView desc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = itemView.findViewById(R.id.hiking_id);
            this.name = itemView.findViewById(R.id.name);
            this.location = itemView.findViewById(R.id.location);
            this.date = itemView.findViewById(R.id.date);
            this.length = itemView.findViewById(R.id.length);
            this.trail = itemView.findViewById(R.id.trail);
            this.parking = itemView.findViewById(R.id.parking);
            this.level = itemView.findViewById(R.id.level);
            this.weather = itemView.findViewById(R.id.weather);
            this.desc = itemView.findViewById(R.id.desc);
        }
    }

    public HikingsAdapter(Context context, List<Hiking> contactList,
                          ObservationAdapter observationAdapter, MainActivity mainActivity) {
        this.context = context;
        this.contactList = contactList;
        this.mainActivity = mainActivity;
        this.observationAdapter = observationAdapter;

        recyclerViewObs = mainActivity.findViewById(R.id.recycler_view_observation);
        recyclerViewObs.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
        recyclerViewObs.setItemAnimator(new DefaultItemAnimator());
        recyclerViewObs.setAdapter(this.observationAdapter);
    }

    @NonNull
    @Override
    public HikingsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hiking_item, parent, false);

        db = new DatabaseHelper(mainActivity);

        Button btnObservation = itemView.findViewById(R.id.btnObservation);
        btnObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Observation> observations = db.getAllObservations();
                if (observations == null || observations.isEmpty()) {
                    Toast.makeText(mainActivity, "Observation is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                observationAdapter.getObservationList().clear();
                observationAdapter.getObservationList().addAll(observations);
                RecyclerView recyclerView = mainActivity.findViewById(R.id.recycler_view_contacts);
                recyclerView.setVisibility(View.GONE);
                mainActivity.findViewById(R.id.recycler_view_search).setVisibility(View.GONE);
                recyclerViewObs.setVisibility(View.VISIBLE);
                observationAdapter.notifyDataSetChanged();
            }
        });

        Button btnAddObs = itemView.findViewById(R.id.btnAddObservation);
        btnAddObs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(mainActivity.getApplicationContext());
                addObsView = layoutInflater.inflate(R.layout.layout_add_observation, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mainActivity);
                alertDialogBuilder.setView(addObsView);

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TextView obserHikeId = mainActivity.findViewById(R.id.hiking_id);
                                TextView obserObservation = addObsView.findViewById(R.id.form_observation_name);
                                TextView obserTime = addObsView.findViewById(R.id.form_observation_time);
                                TextView obserComment = addObsView.findViewById(R.id.form_observation_comment);
                                CreateObservation(obserHikeId.getText().toString(), obserObservation.getText().toString(),
                                        obserTime.getText().toString(), obserComment.getText().toString());
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }
                        );
                alertDialogBuilder.show();
            }
        });

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HikingsAdapter.MyViewHolder holder, int position) {
        final Hiking hiking = contactList.get(position);
        holder.id.setText(String.valueOf(hiking.getId()));
        holder.name.setText(hiking.getName());
        holder.location.setText(hiking.getLocation());
        holder.date.setText(hiking.getDate());
        holder.length.setText(hiking.getLength());
        holder.trail.setText(hiking.getTrail());
        holder.parking.setText(hiking.getParking());
        holder.level.setText(hiking.getLevel());
        holder.weather.setText(hiking.getWeather());
        holder.desc.setText(hiking.getDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.addAnEditHiking(true, hiking, position);
            }
        });


    }
    public void filterList(ArrayList<Hiking> filteredList) {
        contactList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public void addAnEditObservation(final boolean isUpdated, final Observation observation, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity.getApplicationContext());
        View view = layoutInflater.inflate(R.layout.layout_add_observation, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mainActivity);
        alertDialogBuilder.setView(view);

        TextView hikingTitle = view.findViewById(R.id.new_observation_title);

        obserObservation = view.findViewById(R.id.form_observation_name);
        obserTime = view.findViewById(R.id.form_observation_time);
        obserComment = view.findViewById(R.id.form_observation_comment);


        hikingTitle.setText(!isUpdated ? "Add new observation" : "Edit observation");
        if (isUpdated && observation != null) {
            obserObservation.setText(observation.getObservation());
            obserTime.setText(observation.getTime());
            obserComment.setText(observation.getComments());
        }
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(isUpdated ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (TextUtils.isEmpty(obserObservation.getText().toString())) {
                            Toast.makeText(mainActivity, "Please enter a observation", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(obserTime.getText().toString())) {
                            Toast.makeText(mainActivity, "Please enter time", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            dialogInterface.dismiss();
                        }
                        if (isUpdated && observation != null) {
                            UpdateObservation(obserHikeId.getText().toString(), obserObservation.getText().toString(),
                                    obserTime.getText().toString(), obserComment.getText().toString(), position);
                        } else {
                            CreateObservation(obserHikeId.getText().toString(), obserObservation.getText().toString(),
                                    obserTime.getText().toString(), obserComment.getText().toString());
                        }
                    }
                })
                .setNegativeButton(isUpdated ? "Delete" : "Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (isUpdated) {
                                    DeleteObservation(observation, position);
                                } else {
                                    dialogInterface.cancel();
                                }
                            }
                        }
                );
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void DeleteObservation(Observation observation, int position) {

    }

    private void UpdateObservation(String hike_id, String observation, String time, String comment, int position) {

    }

    private void CreateObservation(String hike_id, String observation, String time, String comment) {
        long id = db.insertObservation(hike_id, observation, time, comment);
        Observation obs = db.getObservation(id);
        if (obs != null) {
            observationAdapter.addObservationToList(obs);
        }
    }
}

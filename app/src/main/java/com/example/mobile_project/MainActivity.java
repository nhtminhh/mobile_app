package com.example.mobile_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_project.Adapter.HikingsAdapter;
/*
import com.example.mobile_project.Adapter.ObservationAdapter;
*/
import com.example.mobile_project.Adapter.ObservationAdapter;
import com.example.mobile_project.Adapter.SearchAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText hikingId, hikingName, hikingLocation, hikingDate, hikingLength, hikingParking, hikingLevel,
            hikingTrail, hikingWeather, hikingDesc, editTextSearch;

    EditText obserObservation, obserTime, obserComment;
    TextView obserHikeId;

    private HikingsAdapter hikingsAdapter;
    private ObservationAdapter observationAdapter;

    public List<Hiking> getHikingArrayList() {
        return hikingArrayList;
    }

    public void setHikingArrayList(List<Hiking> hikingArrayList) {
        this.hikingArrayList = hikingArrayList;
    }

    private List<Hiking> hikingArrayList = new ArrayList<>();
    private ArrayList<Observation> observationArrayList = new ArrayList<>();
    private RecyclerView recyclerView;

    private RecyclerView recyclerViewObservation;
    private DatabaseHelper db;

    private MainActivity self;
    private RecyclerView seachView;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        setContentView(R.layout.activity_main);

        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("M-HIKER");

        //
        recyclerView = findViewById(R.id.recycler_view_contacts);
        recyclerViewObservation = findViewById(R.id.recycler_view_observation);
        db = new DatabaseHelper(this);

        seachView = findViewById(R.id.recycler_view_search);
        searchAdapter = new SearchAdapter(this,hikingArrayList, MainActivity.this);
        seachView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        seachView.setItemAnimator(new DefaultItemAnimator());
        seachView.setAdapter(searchAdapter);

        editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || keyEvent == null || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    String keyword = textView.getText().toString();
                    List<Hiking> hikings = db.searchHiking(keyword);
                    searchAdapter.setHikings(hikings);
                    seachView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    recyclerViewObservation.setVisibility(View.GONE);
                    searchAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        //
        hikingArrayList.addAll(db.getAllHiking());
        observationAdapter = new ObservationAdapter(this, observationArrayList, MainActivity.this);
        hikingsAdapter = new HikingsAdapter(this, hikingArrayList, observationAdapter, MainActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(hikingsAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnEditHiking(false, null, -1);
            }
        });

    }

    public void addAnEditHiking(final boolean isUpdated, final Hiking hiking, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.layout_add_hiking, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(view);

        TextView hikingTitle = view.findViewById(R.id.new_contact_title);
        hikingName = view.findViewById(R.id.name);
        hikingLocation = view.findViewById(R.id.location);
        hikingDate = view.findViewById(R.id.date);
        hikingLength = view.findViewById(R.id.length);
        hikingTrail = view.findViewById(R.id.trail);
        hikingParking = view.findViewById(R.id.parking);
        hikingLevel = view.findViewById(R.id.level);
        hikingWeather = view.findViewById(R.id.weather);
        hikingDesc = view.findViewById(R.id.desc);


        hikingTitle.setText(!isUpdated ? "Add new hiking" : "Edit hiking");
        if (isUpdated && hiking != null) {
            hikingName.setText(hiking.getName());
            hikingLocation.setText(hiking.getLocation());
            hikingDate.setText(hiking.getDate());
            hikingLength.setText(hiking.getLength());
            hikingTrail.setText(hiking.getTrail());
            hikingParking.setText(hiking.getParking());
            hikingLevel.setText(hiking.getLevel());
            hikingWeather.setText(hiking.getWeather());
            hikingDesc.setText(hiking.getDesc());

        }
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(isUpdated ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        recyclerView.setVisibility(View.VISIBLE);
                        findViewById(R.id.recycler_view_observation).setVisibility(View.GONE);
                        findViewById(R.id.recycler_view_search).setVisibility(View.GONE);
                        if (TextUtils.isEmpty(hikingName.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(hikingLocation.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Please enter a location", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(hikingDate.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Please enter a date", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(hikingParking.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Please enter parking or not", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(hikingLevel.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Please enter level of difficulty", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(hikingTrail.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Please enter trail type", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            dialogInterface.dismiss();
                        }
                        if (isUpdated && hiking != null) {
                            UpdateHiking(hikingName.getText().toString(), hikingLocation.getText().toString(),
                                    hikingDate.getText().toString(), hikingLength.getText().toString(),
                                    hikingTrail.getText().toString(), hikingParking.getText().toString(),
                                    hikingLevel.getText().toString(), hikingWeather.getText().toString(),
                                    hikingDesc.getText().toString(),
                                    position);
                        } else {
                            CreateContact(hikingName.getText().toString(), hikingLocation.getText().toString(),
                                    hikingDate.getText().toString(), hikingLength.getText().toString(),
                                    hikingTrail.getText().toString(), hikingParking.getText().toString(),
                                    hikingLevel.getText().toString(), hikingWeather.getText().toString(),
                                    hikingDesc.getText().toString());
                        }
                    }
                })
                .setNegativeButton(isUpdated ? "Delete" : "Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (isUpdated) {
                                    DeleteHiking(hiking, position);
                                } else {
                                    dialogInterface.cancel();
                                }
                            }
                        }
                );
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void addAnEditObservation(final boolean isUpdated, final Observation observation, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.layout_add_observation, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(view);

        TextView hikingTitle = view.findViewById(R.id.new_observation_title);
        obserObservation = view.findViewById(R.id.form_observation_name);
        obserTime = view.findViewById(R.id.form_observation_time);
        obserComment = view.findViewById(R.id.form_observation_comment);
        obserHikeId = self.findViewById(R.id.hiking_id);

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
                            Toast.makeText(MainActivity.this, "Please enter a observation", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (TextUtils.isEmpty(obserTime.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Please enter time", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
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

    private void DeleteHiking(Hiking hiking, int position) {
        hikingArrayList.remove(position);
        db.deleteHiking(hiking);
        hikingsAdapter.notifyDataSetChanged();
    }

    private void DeleteObservation(Observation observation, int position) {
        observationArrayList.remove(position);
        db.deleteObservation(observation);
        observationAdapter.notifyDataSetChanged();
    }

    private void UpdateHiking(String name, String location, String date, String length, String trail,
                              String parking, String level, String weather, String desc, int position) {
        Hiking hiking = hikingArrayList.get(position);
        hiking.setName(name);
        hiking.setLocation(location);
        hiking.setDate(date);
        hiking.setLength(length);
        hiking.setTrail(trail);
        hiking.setParking(parking);
        hiking.setLevel(level);
        hiking.setWeather(weather);
        hiking.setDesc(desc);

        db.updateHiking(hiking);
        hikingArrayList.set(position, hiking);
        hikingsAdapter.notifyDataSetChanged();
    }

    private void UpdateObservation(String hike_id, String observation, String time, String comment, int position) {
        Observation observation1 = observationArrayList.get(position);
        observation1.setHikingId(Integer.parseInt(hike_id));
        observation1.setObservation(observation);
        observation1.setTime(time);
        observation1.setComments(comment);

        db.updateObservation(observation1);
        observationArrayList.set(position, observation1);
        observationAdapter.notifyDataSetChanged();
    }

    private void CreateContact(String name, String location, String date, String length,
                               String trail, String parking, String level, String weather,
                               String desc) {
        long id = db.insertHiking(name, location, date, length, trail, parking, level, weather, desc);
        Hiking hiking = db.getHiking(id);
        if (hiking != null) {
            hikingArrayList.add(0, hiking);
            hikingsAdapter.notifyDataSetChanged();
        }
    }

    private void CreateObservation(String hike_id, String observation, String time, String comment) {
        long id = db.insertObservation(hike_id, observation, time, comment);
        Observation observation1 = db.getObservation(id);
        if (observation1 != null) {
            observationArrayList.add(0, observation1);
            observationAdapter.notifyDataSetChanged();
        }
    }

}
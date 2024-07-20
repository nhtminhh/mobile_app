package com.example.mobile_project;

public class Observation {
    public static final String TABLE_NAME = "observations";
    public static final String OBSERVATION_ID = "observation_id";
    public static final String COLUMN_OBSERVATION = "observationTEXTTEXTTEXT";
    public static final String COLUMN_TIME = "observation_time";
    public static final String COLUMN_COMMENTS = "additional_comments";
    public static final String COLUMN_HIKING_ID = "hiking_id";  // Foreign key column

    private int observationId;
    private String observation;
    private String time;
    private String additionalComments;
    private int hikingId;  // Foreign key

    // Constructors
    public Observation(int observationId, String observation, String time, String additionalComments, int hikingId) {
        this.observationId = observationId;
        this.observation = observation;
        this.time = time;
        this.additionalComments = additionalComments;
        this.hikingId = hikingId;
    }

    public Observation() {
    }
    public int getObservationId() {return observationId;}
    public void setObservationId(int observationId) {this.observationId = observationId;}

    public String getObservation() {return observation;}

    public void setObservation(String observation) {this.observation = observation;}

    public String getTime() {return time;}

    public void setTime(String time) {this.time = time;}

    public String getComments() {return additionalComments;}

    public void setComments(String additionalComments) {this.additionalComments = additionalComments;}

    public int getHikingId() {return hikingId;}

    public void setHikingId(int hikingId) {this.hikingId = hikingId;}


    // CREATE_TABLE SQL Query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + OBSERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_OBSERVATION + "TEXT NOT NULL, "
                    + COLUMN_TIME + " TEXT NOT NULL, "
                    + COLUMN_COMMENTS + " TEXT NOT NULL, "
                    + COLUMN_HIKING_ID + " INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + COLUMN_HIKING_ID + ") REFERENCES " + Hiking.TABLE_NAME + "(" + Hiking.COLUMN_ID + ")"
                    + ");";
}
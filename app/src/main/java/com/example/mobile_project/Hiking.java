package com.example.mobile_project;


public class Hiking {
    public static final String TABLE_NAME = "hikings";
    public static final String COLUMN_ID = "hiking_id";
    public static final String COLUMN_NAME = "hiking_name";
    public static final String COLUMN_LOCATION = "hiking_location";
    public static final String COLUMN_DATE = "hiking_date";
    public static final String COLUMN_LENGTH = "hiking_length";
    public static final String COLUMN_TRAIL = "hiking_trail";
    public static final String COLUMN_PARKING = "hiking_parking";
    public static final String COLUMN_LEVEL = "hiking_level";
    public static final String COLUMN_WEATHER = "hiking_weather";
    public static final String COLUMN_DESC = "hiking_desc";

    private String name;
    private String location;
    private String date;
    private String length;
    private String trail;
    private String parking;
    private String level;
    private String weather;
    private String desc;
    private long id;

    public Hiking(long id, String name, String location, String date, String length, String trail,
                  String parking, String level, String weather, String desc){
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.length = length;
        this.trail = trail;
        this.parking = parking;
        this.level = level;
        this.weather = weather;
        this.desc = desc;
    }
    public Hiking(){
    }

    public  long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getLocation(){return location;}
    public void setLocation(String location){this.location = location;}

    public String getDate(){return date;}
    public void setDate(String date){this.date = date;}

    public String getLength(){return length;}
    public void setLength(String length){this.length = length;}

    public String getTrail(){return trail;}
    public void setTrail(String trail){this.trail = trail;}


    public String getParking(){return parking;}
    public void setParking(String parking){this.parking = parking;}

    public String getLevel(){return level;}
    public void setLevel(String level){this.level = level;}

    public String getWeather(){return weather;}
    public void setWeather(String weather){this.weather = weather;}

    public String getDesc(){return desc;}
    public void setDesc(String desc){this.desc = desc;}


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT NOT NULL,"
                    + COLUMN_LOCATION + " TEXT NOT NULL, "
                    + COLUMN_DATE + " TEXT NOT NULL, "
                    + COLUMN_LENGTH + " TEXT NOT NULL, "
                    + COLUMN_TRAIL + " TEXT NOT NULL, "
                    + COLUMN_PARKING + " TEXT NOT NULL, "
                    + COLUMN_LEVEL + " TEXT NOT NULL, "
                    + COLUMN_WEATHER + " TEXT NOT NULL, "
                    + COLUMN_DESC + " TEXT NOT NULL"
                    + ")";

}


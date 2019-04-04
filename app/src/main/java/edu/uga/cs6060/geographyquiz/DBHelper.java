package edu.uga.cs6060.geographyquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLiteOpenHelper subclass to create and access our database.
 * Database schema is defined here.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "geography_quiz.db";
    private static final int DB_VERSION = 1;
    private static DBHelper helperInstance;

    // Table Names
    public static final String TABLE_COUNTRIES = "countries";
    public static final String TABLE_CONTINENTS = "continents";
    public static final String TABLE_NEIGHBORS = "neighbors";
    public static final String TABLE_RESULTS = "results";

    // Column Names
    // Countries table columns
    public static final String COUNTRIES_ID = "_id";
    public static final String COUNTRIES_CONTINENT_ID = "contient_id";
    public static final String COUNTRIES_NAME = "name";

    // Continents table columns
    public static final String CONTINENTS_ID = "_id";
    public static final String CONTINENTS_NAME = "name";

    // Association Table Neighbors between Countries and Continents
    // Neighbors table columns
    public static final String NEIGHBORS_ID = "_id";
    public static final String NEIGHBORS_COUNTRY_ID = "country_id";
    public static final String NEIGHBORS_NEIGHBOR_ID = "neighbor_id";

    // Results table columns
    // TODO: define Results table
    public static final String RESULTS_ID = "_id";

    // Define Queries to create Tables
    public static final String CREATE_COUNTRIES = "create table " + TABLE_COUNTRIES + "("
            + COUNTRIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COUNTRIES_CONTINENT_ID + " INTEGER, "
            + COUNTRIES_NAME + " TEXT, "
            + "CONSTRAINT fk_continents FOREIGN KEY (" + COUNTRIES_CONTINENT_ID + ")"
            + "REFERENCES " + TABLE_CONTINENTS + "(" + CONTINENTS_ID + ")"
            + ")";

    public static final String CREATE_CONTINENTS = "create table " + TABLE_CONTINENTS + "("
            + CONTINENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CONTINENTS_NAME + " TEXT"
            + ")";

    public static final String CREATE_NEIGHBORS = "create table " + TABLE_NEIGHBORS + "("
            + NEIGHBORS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NEIGHBORS_COUNTRY_ID + " INTEGER, "
            + NEIGHBORS_NEIGHBOR_ID + " INTEGER, "
            + "CONSTRAINT fk_countries FOREIGN KEY (" + NEIGHBORS_COUNTRY_ID + ")"
            + "REFERENCES " + TABLE_COUNTRIES + "(" + COUNTRIES_ID + "), "
            + "CONSTRAINT fk_neighbors FOREIGN KEY (" + NEIGHBORS_NEIGHBOR_ID + ")"
            + "REFERENCES " + TABLE_COUNTRIES + "(" + COUNTRIES_ID + ")"
            + ")";

    /**
     * Creates a DBHelper using parent class SQLiteOpenHelper constructor
     * @param context   Activity context
     */
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Create a new instance of DBHelper
     * @param context   Activity context
     * @return          Instance of a DBHelper
     */
    public static synchronized DBHelper getInstance(Context context) {
        if (helperInstance == null) {
            helperInstance = new DBHelper(context.getApplicationContext());
        }
        return helperInstance;
    }

    /**
     * Create database using CREATE String variables
     * @param db    SQLite database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTINENTS);
        db.execSQL(CREATE_COUNTRIES);
        db.execSQL(CREATE_NEIGHBORS);
    }

    /**
     * Update given database by dropping all tables and calling onCreate again
     * @param db            SQLite database
     * @param oldVersion    Old version number of database
     * @param newVersion    New version number of database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_COUNTRIES);
        db.execSQL("drop table if exists " + TABLE_CONTINENTS);
        db.execSQL("drop table if exists " + TABLE_NEIGHBORS);
        db.execSQL("drop table if exists " + TABLE_RESULTS);
        onCreate(db);
    }
}

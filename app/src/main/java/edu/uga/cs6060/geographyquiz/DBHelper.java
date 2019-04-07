package edu.uga.cs6060.geographyquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

/**
 * SQLiteOpenHelper subclass to create and access our database.
 * Database schema is defined here.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHELPER";

    private static final String DB_NAME = "geography_quiz.db";
    private static final int DB_VERSION = 1;
    private static DBHelper helperInstance;

    // Table Names
    public static final String TABLE_QUESTIONS = "QUESTIONS";
    public static final String TABLE_QUIZZES = "QUIZZES";
    public static final String TABLE_RELATIONSHIPS = "RELATIONSHIPS";

    // Column Names
    // QUESTIONS table columns
    public static final String QUESTIONS_ID = "_id";
    public static final String QUESTIONS_COUNTRY = "country";
    public static final String QUESTIONS_CONTINENT = "continent";
    public static final String QUESTIONS_NEIGHBOR = "neighbor";

    public static final String RELATIONSHIPS_ID = "_id";
    public static final String RELATIONSHIPS_QUIZ_ID = "quiz_id";
    public static final String RELATIONSHIPS_QUESTION_ID = "question_id";

    public static final String QUIZZES_ID = "_id";
    public static final String QUIZZES_DATE = "date";
    public static final String QUIZZES_RESULT = "result";


    // Results table columns
    // TODO: define Results table
    public static final String RESULTS_ID = "_id";
    public static final String RESULTS_DATE = "quiz_date";
    public static final String RESULTS_RESULT = "result";

    // Define Queries to create Tables
    public static final String CREATE_QUESTIONS = "create table if not exists " + TABLE_QUESTIONS + "("
            + QUESTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + QUESTIONS_CONTINENT+ " TEXT, "
            + QUESTIONS_COUNTRY + " TEXT, "
            + QUESTIONS_NEIGHBOR + " TEXT"
            + ")";

    public static final String CREATE_RELATIONSHIPS = "create table if not exists " + TABLE_RELATIONSHIPS + "("
            + RELATIONSHIPS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + RELATIONSHIPS_QUIZ_ID + " INTEGER, "
            + RELATIONSHIPS_QUESTION_ID + " INTEGER, "
            + "CONSTRAINT fk_question FOREIGN KEY (" + RELATIONSHIPS_QUESTION_ID + ")"
            + "REFERENCES " + TABLE_QUESTIONS + "(" + QUESTIONS_ID + "), "
            + "CONSTRAINT fk_quiz FOREIGN KEY (" + RELATIONSHIPS_QUIZ_ID + ")"
            + "REFERENCES " + TABLE_QUIZZES + "(" + QUIZZES_ID + ")"
            + ")";

    public static final String CREATE_QUIZZES = "create table if not exists " + TABLE_QUIZZES + "("
            + QUIZZES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUIZZES_DATE + " TEXT, "
            + QUIZZES_RESULT + " INTEGER"
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
            Log.d(TAG, "New Helper made");
        }
        return helperInstance;
    }

    /**
     * @author  Tripp
     * Create database using CREATE String variables
     * @param db    SQLite database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUESTIONS);
        db.execSQL(CREATE_QUIZZES);
        db.execSQL(CREATE_RELATIONSHIPS);
        Log.d(TAG, "Tables Created");

    }

    /**
     * Update given database by dropping all tables and calling onCreate again
     * @param db            SQLite database
     * @param oldVersion    Old version number of database
     * @param newVersion    New version number of database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_QUESTIONS);
        db.execSQL("drop table if exists " + TABLE_RELATIONSHIPS);
        db.execSQL("drop table if exists " + TABLE_QUIZZES);
        Log.d(TAG, "onUpgrade called");
        onCreate(db);
    }
}

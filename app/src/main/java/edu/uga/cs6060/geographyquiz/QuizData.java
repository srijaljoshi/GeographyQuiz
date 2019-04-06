package edu.uga.cs6060.geographyquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuizData {

    public static final String TAG = "QuizData";

    private SQLiteOpenHelper helper;
    private SQLiteDatabase db;

    public QuizData(Context context) {
        this.helper = DBHelper.getInstance(context);
    }

    public void open() {
        db = helper.getWritableDatabase();
        helper.onCreate(db);
        Log.d(TAG, "DB opened " + db.toString());
    }

    public void close() {
        if (helper != null) {
            helper.close();
        }
    }

    /**
     * @author  Tripp
     * Gets a list of six questions for the quiz by querying the
     * database through SQLiteOpenHelper object
     * @return  ArrayList of six questions
     */
    public List<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();

        // For loop to create six questions in our list
        // TODO: Create logic to randomly get 6 questions from DB and store them
        for (int i =0; i < 6; i++) {
            int random = (int) (Math.random() * 195);    // Variable to pick random country id

        }

        return questions;
    }

    /**
     * @author  Tripp
     * This method should read country_continent CSV file to create our Country and Continent Table
     * @param in_s  InputStream object made from Resources in Activity
     */
    public void storeBasicQuestions(InputStream in_s) {

        ContentValues values;
        ContentValues continent_values;
        Cursor cursor;
        String continent;
        String[] columns = {"name"};

        try {

            CSVReader csvReader = new CSVReader(new InputStreamReader(in_s));
            String nextLine[];
            Log.d(TAG, "In try statement");

            while ((nextLine = csvReader.readNext()) != null) {

                Log.d(TAG, "In while statement");

                values = null;
                continent_values = null;
                long continent_id = -1;

                continent = nextLine[1];
                Log.d(TAG, "Continent is " + continent);
                continent_values.put(DBHelper.CONTINENTS_NAME, continent);
                cursor = db.query(DBHelper.TABLE_CONTINENTS, columns, "name = " + continent, null,
                        null, null, null);

                Log.d(TAG, "storeBasicQuestions: Column index: " + cursor.getLong(cursor.getColumnIndex(DBHelper.CONTINENTS_ID)));

                while (cursor.moveToNext()) {
                    continent_id = cursor.getLong(cursor.getColumnIndex(DBHelper.CONTINENTS_ID));
                    Log.d(TAG, "storeBasicQuestions: Continent ID: " + continent_id);
                }

                if (continent_id < 0) {
                    // Query found no continent, so we make a new one
                    continent_id = db.insert(DBHelper.TABLE_CONTINENTS, null , continent_values);
                }

                else {
                    Log.d(TAG, "storeBasicQuestions: ID created in Continents: " + continent_id);
                }

                values.put(DBHelper.COUNTRIES_CONTINENT_ID, continent_id);

                values.put(DBHelper.COUNTRIES_NAME, nextLine[0]);

                long id = db.insert(DBHelper.TABLE_COUNTRIES, null, values);
                Log.d(TAG, "storeBasicQuestions: ID created in Countries: " + id);

            }
        }

        catch (Exception e) {
            Log.d(TAG, "storeBasicQuestions: Exception in storeBasicQuestions()");
            Log.d(TAG, e.toString());
        }
    }

    /**
     * @author  Tripp
     * This method should read country_neighbors CSV file to create our Neighbors Table
     * @param in_s  InputStream object made from Resources in Activity
     */
    public void storeAdvancedQuestions(InputStream in_s) {

        ContentValues values;
        Cursor cursor;
        String[] columns = {"_id"};
        String country;

        try {

            CSVReader csvReader = new CSVReader(new InputStreamReader(in_s));
            String nextLine[];

            while ((nextLine = csvReader.readNext()) != null) {

                country = nextLine[0];
                values = null;
                long country_id = -1;
                long neighbor_id = -1;

                cursor = db.query(DBHelper.TABLE_COUNTRIES, columns, "name = " + country,
                        null, null, null, null);

                while (cursor.moveToNext()) {
                    country_id = cursor.getLong(cursor.getColumnIndex(DBHelper.COUNTRIES_ID));
                }

                cursor.close();

                values.put(DBHelper.NEIGHBORS_COUNTRY_ID, country_id);

                for (int i = 1; i < nextLine.length; i++) {
                    country = nextLine[i];
                    cursor = db.query(DBHelper.TABLE_COUNTRIES, columns, "name = " + country,
                            null, null, null, null);

                    while(cursor.moveToNext()) {
                        neighbor_id = cursor.getLong(cursor.getColumnIndex(DBHelper.COUNTRIES_ID));
                    }
                    cursor.close();

                    values.put(DBHelper.NEIGHBORS_NEIGHBOR_ID, neighbor_id);
                    long result = db.insert(DBHelper.TABLE_NEIGHBORS, null, values);
                    Log.d(TAG, "New Neighbor Id: " + result);

                    values = null;
                    values.put(DBHelper.NEIGHBORS_COUNTRY_ID, country_id);
                }
            }
        }

        catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
}
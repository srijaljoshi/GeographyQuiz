package edu.uga.cs6060.geographyquiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizData {

    public static final String TAG = "QuizData";

    private DBHelper helper;
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

    public void populateDatabase(Resources res) {

        Cursor cursor = db.rawQuery("SELECT count(*) FROM countries", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        if (count <=0 ) {
            InputStream in_s = res.openRawResource(R.raw.country_continent);
            storeBasicQuestions(in_s);

            in_s = res.openRawResource(R.raw.country_neighbors);
            storeAdvancedQuestions(in_s);
        }

        else {
            Log.d(TAG, "Data already existed");
        }

    }

    /**
     * @author  Tripp
     * This method should read country_continent CSV file to create our Country and Continent Table
     * @param in_s  InputStream object made from Resources in Activity
     */
    private void storeBasicQuestions(InputStream in_s) {

        ContentValues values = new ContentValues();
        String continent;
        String country;

        try {

            CSVReader csvReader = new CSVReader(new InputStreamReader(in_s));
            String nextLine[];
            Log.d(TAG, "In try statement");

            while ((nextLine = csvReader.readNext()) != null) {

                country = nextLine[0];
                continent = nextLine[1];

                values.put(DBHelper.COUNTRIES_NAME, country);
                values.put(DBHelper.COUNTRIES_CONTINENT, continent);

                long id = db.insert(DBHelper.TABLE_COUNTRIES, null, values);
                Log.d(TAG, "storeBasicQuestions: ID created in Countries: " + id);

            }
        } catch (Exception e) {
            Log.d(TAG, "storeBasicQuestions: Exception in storeBasicQuestions()");
            Log.d(TAG, e.toString());
        }
    }

    /**
     * @author  Tripp
     * This method should read country_neighbors CSV file to create our Neighbors Table
     * @param in_s  InputStream object made from Resources in Activity
     */
    private void storeAdvancedQuestions(InputStream in_s) {

        ContentValues values;
        Cursor cursor;
        String[] columns = {"_id"};
        String country;
        long country_id;
        long neighbor_id;

        try {

            CSVReader csvReader = new CSVReader(new InputStreamReader(in_s));
            String nextLine[];

            while ((nextLine = csvReader.readNext()) != null) {

                country_id = -1;
                country = nextLine[0];
                values = new ContentValues();


                cursor = db.query(DBHelper.TABLE_COUNTRIES, columns, "name = ?",
                        new String[]{country}, null, null, null);

                while (cursor.moveToNext()) {
                    country_id = cursor.getLong(cursor.getColumnIndex(DBHelper.COUNTRIES_ID));
                }

                cursor.close();
                if (country_id != -1) {
                    Log.d(TAG, "Found Country " + country + ", ID: " + country_id);
                }

                values.put(DBHelper.NEIGHBORS_COUNTRY_ID, country_id);

                for (int i = 1; i < nextLine.length; i++) {

                    neighbor_id = -1;

                    country = nextLine[i];
                    cursor = db.query(DBHelper.TABLE_COUNTRIES, columns, "name = ?",
                            new String[]{country}, null, null, null);

                    while(cursor.moveToNext()) {
                        neighbor_id = cursor.getLong(cursor.getColumnIndex(DBHelper.COUNTRIES_ID));
                    }
                    cursor.close();
                    if (neighbor_id != -1) {
                        Log.d(TAG, "Found Neighbor " + country + ", ID: " + neighbor_id);
                    }
                    else {
                        break;
                    }

                    values.put(DBHelper.NEIGHBORS_NEIGHBOR_ID, neighbor_id);
                    long result = db.insert(DBHelper.TABLE_NEIGHBORS, null, values);
                    Log.d(TAG, "New Neighbor Id: " + result);

                    //values = null;
                    //values.put(DBHelper.NEIGHBORS_COUNTRY_ID, country_id);
                }
            }
        }

        catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
}

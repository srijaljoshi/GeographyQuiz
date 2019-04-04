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
    }

    public void close() {
        if (helper != null) {
            helper.close();
        }
    }

    /**
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
     * This method should read country_continent CSV files to create our database
     * @param in_s  InputStream object made from Resources in Activity
     */
    public void storeBasicQuestions(InputStream in_s) {

        ContentValues values;
        Cursor cursor;

        try {

            CSVReader csvReader = new CSVReader(new InputStreamReader(in_s));
            String nextLine[];

            while ((nextLine = csvReader.readNext()) != null) {
                values = null;

                values.put(DBHelper.COUNTRIES_NAME, nextLine[0]);
                //values.put(DBHelper.COUNTRIES_CONTINENT_ID, nextLine[1]);



                long id = db.insert(DBHelper.TABLE_COUNTRIES, null, values);
                Log.d(TAG, "ID created in Countries: " + id);

                // TODO: Need to see if given continent exists
            }
        }

        catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    public void storeAdvancedQuestions(InputStream in_s) {

        ContentValues values;
        Cursor cursor;
        String[] columns = {"_id"};
        String country;

        try {

            CSVReader csvReader = new CSVReader(new InputStreamReader(in_s));
            String nextLine[];

            while ((nextLine = csvReader.readNext()) != null) {

                // TODO: Query DB to find Country IDs
                values = null;
                String query = "SELECT _id FROM " + DBHelper.TABLE_COUNTRIES + " WHERE name = " + nextLine[0];
                cursor = db.query(DBHelper.TABLE_COUNTRIES, columns, null, null, null, null, null);

                values.put(DBHelper.COUNTRIES_NAME, nextLine[0]);
                values.put(DBHelper.COUNTRIES_CONTINENT_ID, nextLine[1]);

                long id = db.insert(DBHelper.TABLE_COUNTRIES, null, values);
                Log.d(TAG, "ID created in Countries: " + id);
            }
        }

        catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
}

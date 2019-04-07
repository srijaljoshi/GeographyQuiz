package edu.uga.cs6060.geographyquiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opencsv.CSVReader;

import org.apache.commons.lang3.ArrayUtils;

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
     * @return ArrayList of six questions
     * @author Tripp
     * Gets a list of six questions for the quiz by querying the
     * database through SQLiteOpenHelper object
     */
    public List<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();
        String country;
        String[] countries = new String[12];
        Cursor cursor;

        // For loop to create six questions in our list
        for (int i = 0; i < 12; i++) {

            boolean run = true;

            while (run) {
                int random = (int) (Math.random() * 599);    // Variable to pick random country id
                cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_QUESTIONS + " WHERE _id = ?", new String[]{"" + random});
                cursor.moveToFirst();
                country = cursor.getString(cursor.getColumnIndex(DBHelper.QUESTIONS_COUNTRY));
                Log.d(TAG, "Country found: " + country);

                if (!ArrayUtils.contains(countries, country)) {
                    countries[i] = country;
                    Question q = new Question(country, cursor.getString(cursor.getColumnIndex(DBHelper.QUESTIONS_CONTINENT)),
                            cursor.getString(cursor.getColumnIndex(DBHelper.QUESTIONS_NEIGHBOR)));
                    questions.add(q);
                    run = false;
                }
            }

        }

        for (Question q : questions) {
            System.out.println(q.getCountry() + " " + q.getContinent_answer() + " " + q.getNeighbor_answer());
        }
        return questions;
    }

    public void populateDatabase(Resources res) {

        Cursor cursor = db.rawQuery("SELECT count(*) FROM questions", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        if (count <= 0) {
            storeNeighbors(res);
        } else {
            Log.d(TAG, "Data already existed");
        }
        cursor.close();
    }

    /**
     * @param res The raw resource (csv file) passed from the calling activity
     * @author Tripp
     * This method should read country_continent CSV file to create our Country and Continent Table
     */
    private void storeNeighbors(Resources res) {

        ContentValues values = new ContentValues();
        String continent;
        String country;
        String neighbor;
        long id;


        try {

            CSVReader csvReader = new CSVReader(new InputStreamReader(res.openRawResource(R.raw.country_neighbors)));
            String nextLine[];
            Log.d(TAG, "In try statement");

            while ((nextLine = csvReader.readNext()) != null) {

                country = nextLine[0];
                continent = null;

                values.put(DBHelper.QUESTIONS_COUNTRY, country);
                values.put(DBHelper.QUESTIONS_CONTINENT, continent);

                for (int i = 1; i < nextLine.length; i++) {
                    neighbor = nextLine[i];
                    if (!neighbor.equals("")) { // ignore if the csv file has consecutive commas
                        values.put(DBHelper.QUESTIONS_NEIGHBOR, neighbor);
                        id = db.insert(DBHelper.TABLE_QUESTIONS, null, values);
                        Log.d(TAG, "Question made, ID: " + id);
                    } else {
                        break;
                    }
                }
            }

            // The continent column will be null after populating countries and neighbors
            // That is why we update the continent column separately
            updateContinents(res);
        } catch (Exception e) {
            Log.d(TAG, "storeBasicQuestions: Exception in storeBasicQuestions()");
            Log.d(TAG, e.toString());
        }
    }

    private void updateContinents(Resources res) {

        String country;
        String continent;
        ContentValues values = new ContentValues();
        long id;

        try {

            CSVReader csvReader = new CSVReader(new InputStreamReader(res.openRawResource(R.raw.country_continent)));
            String nextLine[];
            Log.d(TAG, "In try statement");

            while ((nextLine = csvReader.readNext()) != null) {
                country = nextLine[0];
                continent = nextLine[1];

                values.put(DBHelper.QUESTIONS_CONTINENT, continent);

                id = db.update(DBHelper.TABLE_QUESTIONS, values, "country = ?", new String[]{country});
                Log.d(TAG, "ID " + id + " updated. Continent now: " + continent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
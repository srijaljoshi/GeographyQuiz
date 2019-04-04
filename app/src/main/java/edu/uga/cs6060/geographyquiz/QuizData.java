package edu.uga.cs6060.geographyquiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuizData {

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
     * This method should read the CSV files to create our database
     * @param in_s  InputStream object made from Resources in Activity
     */
    public void storeQuestions(InputStream in_s) {

        ContentValues values = new ContentValues();

        try {

            CSVReader csvReader = new CSVReader(new InputStreamReader(in_s));
            String nextLine[];

            while ((nextLine = csvReader.readNext()) != null) {
                values = null;

                db.execSQL("SELECT name FROM " + DBHelper.TABLE_COUNTRIES + " WHERE name = " + nextLine[0]);
                // TODO: Wrong, need to get name and see if it exists
                values.put(DBHelper.COUNTRIES_NAME, nextLine[0]);
                values.put(DBHelper.COUNTRIES_CONTINENT_ID, nextLine[1]);
            }
        }

        catch (Exception e) {

        }
    }
}

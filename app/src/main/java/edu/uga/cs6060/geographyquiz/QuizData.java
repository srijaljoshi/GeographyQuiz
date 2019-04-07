package edu.uga.cs6060.geographyquiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.opencsv.CSVReader;

import org.apache.commons.lang3.ArrayUtils;

import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
     * @return  List of six questions
     */
    public List<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();
        String country;
        String[] countries = new String[6];
        Cursor cursor;

        String[] continents = {"Asia", "Europe", "Africa", "North America", "South America", "Oceania"};

        // For loop to create six questions in our list
        for (int i =0; i < 6; i++) {

            boolean run = true;
            boolean wrongAnswerNotSet;

            while (run) {
                int random = (int) (Math.random() * 599) + 1;    // Variable to pick random country id
                cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABLE_QUESTIONS + " WHERE _id = ?", new String[]{"" + random});
                cursor.moveToFirst();
                country = cursor.getString(cursor.getColumnIndex(DBHelper.QUESTIONS_COUNTRY));
                Log.d(TAG, "Country found: " + country);

                if (!ArrayUtils.contains(countries, country)) {
                    countries[i] = country;
                    Question q = new Question(cursor.getString(cursor.getColumnIndex(DBHelper.QUESTIONS_ID)), country, cursor.getString(cursor.getColumnIndex(DBHelper.QUESTIONS_CONTINENT)),
                            cursor.getString(cursor.getColumnIndex(DBHelper.QUESTIONS_NEIGHBOR)));

                    wrongAnswerNotSet = true;
                    // Set first wrong answer as long as it is not the answer
                    while (wrongAnswerNotSet) {

                        int wrongAnswerRandom = (int) ((Math.random() * 5));

                        if (!continents[wrongAnswerRandom].equals(q.getContinent_answer())) {
                            q.setWrong_continent_1(continents[wrongAnswerRandom]);
                            wrongAnswerNotSet = false;
                        }
                    }

                    wrongAnswerNotSet = true;

                    // Set second wrong answer as long as it is not the answer or the previous
                    // wrong answer
                    while (wrongAnswerNotSet) {
                        int wrongAnswerRandom = (int) ((Math.random() * 5));

                        if (!continents[wrongAnswerRandom].equals(q.getContinent_answer()) &&
                                !continents[wrongAnswerRandom].equals(q.getWrong_continent_1())) {
                            q.setWrong_continent_2(continents[wrongAnswerRandom]);
                            wrongAnswerNotSet = false;
                        }
                    }

                    ArrayList<String> neighbors = new ArrayList<String>();
                    Cursor neighborsCursor = db.rawQuery("SELECT neighbor FROM "
                            + DBHelper.TABLE_QUESTIONS + " WHERE country = ?", new String[]{q.getCountry()});
                    neighborsCursor.moveToFirst();
                    while(!neighborsCursor.isAfterLast()) {
                        neighbors.add(neighborsCursor.getString(neighborsCursor.getColumnIndex("neighbor")));
                        neighborsCursor.moveToNext();
                    }
                    neighborsCursor.close();

                    wrongAnswerNotSet = true;

                    while (wrongAnswerNotSet) {
                        int wrongAnswerRandom = (int) (Math.random() * 599);
                        Cursor countryCursor = db.rawQuery("SELECT " + DBHelper.QUESTIONS_COUNTRY + " FROM "
                                + DBHelper.TABLE_QUESTIONS + " WHERE " + DBHelper.QUESTIONS_ID + " = ?", new String[]{"" + wrongAnswerRandom});
                        countryCursor.moveToFirst();

                        String selection = countryCursor.getString(countryCursor.getColumnIndex("country"));

                        countryCursor.close();

                        boolean update = true;

                        if (!selection.equals(q.getCountry())) {
                            for (String s : neighbors) {
                                if (s.equals(selection)) {
                                    update = false;
                                    break;
                                }
                            }

                            if (update) {
                                wrongAnswerNotSet = false;
                                q.setWrong_neighbor_1(selection);
                            }
                        }
                    }

                    wrongAnswerNotSet = true;

                    while (wrongAnswerNotSet) {
                        int wrongAnswerRandom = (int) (Math.random() * 599);
                        Log.d(TAG, "Random Integer: " + wrongAnswerRandom);
                        Cursor countryCursor = db.rawQuery("SELECT country FROM "
                                + DBHelper.TABLE_QUESTIONS + " WHERE _id = ?", new String[]{"" + wrongAnswerRandom});
                        countryCursor.moveToFirst();

                        String selection = countryCursor.getString(countryCursor.getColumnIndex("country"));

                        countryCursor.close();

                        boolean update = true;

                        if (!selection.equals(q.getCountry()) && !selection.equals(q.getWrong_neighbor_1())) {
                            for (String s : neighbors) {
                                if (s.equals(selection)) {
                                    update = false;
                                    break;
                                }
                            }

                            if (update) {
                                wrongAnswerNotSet = false;
                                q.setWrong_neighbor_2(selection);
                            }
                        }
                    }

                    questions.add(q);
                    run = false;
                }
            }

        }

        for (Question q : questions) {
            System.out.println(q.getCountry() + " " + q.getContinent_answer() + " " + q.getNeighbor_answer());
            System.out.println("Wrong answers: " + q.getWrong_continent_1() + " " + q.getWrong_continent_2() + " " + q.getWrong_neighbor_1() + " " + q.getWrong_neighbor_2());
        }
        return questions;
    }

    /**
     * @author  Tripp
     * Method to make an entry in the Quiz table and uses passed in List of Question objects to
     * build Relationship table, tieing Questions with our new Quiz
     * @param   list  List of 6 Question objects
     * @return  returns id of Quiz entry
     */
    public long makeQuizEntry(List<Question> list) {

        ContentValues values = new ContentValues();
        values.put(DBHelper.QUIZZES_DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        long id = db.insert(DBHelper.TABLE_QUIZZES, null, values);
        Log.d(TAG, "New Quiz ID: " + id);

        values = new ContentValues();
        values.put(DBHelper.RELATIONSHIPS_QUIZ_ID, id);

        for (Question q : list) {
            values.put(DBHelper.RELATIONSHIPS_QUESTION_ID, q.getId());
            long relID = db.insert(DBHelper.TABLE_RELATIONSHIPS, null, values);
            Log.d(TAG, "New relationship ID: " + relID);
        }

        return id;
    }

    /**
     * @author  Tripp
     * Method to check if data exists in database and call DB creation methods if not
     * @param res   Activity Resources used to get CSV files
     */
    public void populateDatabase(Resources res) {

        Cursor cursor = db.rawQuery("SELECT count(*) FROM questions", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        if (count <=0 ) {
            storeNeighbors(res);
        }

        else {
            Log.d(TAG, "Data already existed");
        }

    }

    /**
     * @author  Tripp
     * This method should read country_neighbors CSV file to create Questions table
     * @param res   Application Resources used to access CSV files
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
                id = -1;

                values.put(DBHelper.QUESTIONS_COUNTRY, country);
                values.put(DBHelper.QUESTIONS_CONTINENT, continent);

                for (int i = 1; i < nextLine.length; i++) {
                    neighbor = nextLine[i];
                    if (!neighbor.equals("")) {
                        values.put(DBHelper.QUESTIONS_NEIGHBOR, neighbor);
                        id = db.insert(DBHelper.TABLE_QUESTIONS, null, values);
                        Log.d(TAG, "Question made, ID: " + id);
                    }
                    else {
                        break;
                    }
                }
            }

            updateContinents(res);
        } catch (Exception e) {
            Log.d(TAG, "storeBasicQuestions: Exception in storeBasicQuestions()");
            Log.d(TAG, e.toString());
        }
    }

    /**
     * @author  Tripp
     * This method should read country_continent CSV file to update Questions table with country's
     * continent
     * @param res   Application Resources used to access CSV files
     */
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

        }

        catch (Exception e) {
            Log.d(TAG, "Exception: " + e.toString());
        }
    }

    /**
     *
     * @param id
     * @param result
     */
    private void storeResults(long id, int result) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.QUIZZES_RESULT, result);
        db.update(DBHelper.TABLE_QUIZZES, values, DBHelper.QUIZZES_ID + " = ?", new String[]{"" + id} );
    }

}

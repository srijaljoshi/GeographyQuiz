package edu.uga.cs6060.geographyquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public List<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();

        // For loop to create six questions in our list
        // TODO: Create logic to randomly get 6 questions from DB and store them
        for (int i =0; i < 6; i++) {
            int random = (int) (Math.random() * 10);    // Variable to pick random country id
        }

        return questions;
    }
}

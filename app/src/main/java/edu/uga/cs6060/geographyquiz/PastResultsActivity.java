package edu.uga.cs6060.geographyquiz;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class PastResultsActivity extends AppCompatActivity {

    private static final String TAG = "PastResultsActivity";
    QuizData quizData;
    List<Quiz> quizzes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_results);

        quizData = new QuizData(this);

        new AsyncQuizResultsTask().execute();

    }

    public void logQuizzes() {
        for (Quiz quiz : quizzes) {
            if (quiz.getResult() != null) {
                Log.d(TAG, "_id: " + quiz.get_id() + " date: " + quiz.getDate() + " result: " + quiz.getResult());
            }
        }
    }


    private class AsyncQuizResultsTask extends AsyncTask<Void, Void, List<Quiz>> {

        @Override
        protected List<Quiz> doInBackground(Void... voids) {
            quizData.open();
            quizzes = quizData.getQuizResults();
            return quizzes;
        }

        @Override
        protected void onPostExecute(List<Quiz> quizzes) {
            super.onPostExecute(quizzes);
            logQuizzes();
        }
    }

}

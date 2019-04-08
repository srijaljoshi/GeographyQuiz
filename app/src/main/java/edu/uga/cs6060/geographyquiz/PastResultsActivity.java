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
        quizData.open();
        quizzes = quizData.getQuizResults();
        logQuizzes();
//        new AsyncQuizResultsTask().execute();

    }

    public void logQuizzes() {
        for (Quiz quiz : quizzes) {
            if (quiz.getResult() != null) {
//                Log.d(TAG, "_id: " + quiz.get_id() + " date: " + quiz.getDate() + " result: " + quiz.getResult());
            }
        }

        System.out.println("_id: " + quizzes.get(0).get_id() + " date: " + quizzes.get(0).getDate() + " result: " + quizzes.get(0).getResult());
        System.out.println("_id: " + quizzes.get(1).get_id() + " date: " + quizzes.get(1).getDate() + " result: " + quizzes.get(1).getResult());
        System.out.println("_id: " + quizzes.get(2).get_id() + " date: " + quizzes.get(2).getDate() + " result: " + quizzes.get(2).getResult());
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
//            logQuizzes();
        }
    }

}

package edu.uga.cs6060.geographyquiz;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class QuizQuestionActivity extends AppCompatActivity {

    static final int NUM_ITEMS = 13;

    MyAdapter mAdapter;

    ViewPager mPager;

    QuizData quizData;
    static List<Question> questions;

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        quizData = new QuizData(this);
        new AsyncQuizLoaderTask().execute();

    }

    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm, List<Question> list) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return QuizQuestionFragment.newInstance(position, questions);
        }
    }

    private class AsyncQuizLoaderTask extends AsyncTask<Void, Void, List<Question>> {

        /**
         * Loads the list of questions from the database and stores it as a list of questions
         * @param voids Nothing needs to be passed, really
         * @return list of questions
         */
        @Override
        protected List<Question> doInBackground(Void... voids) {
            quizData.open();
            questions = quizData.getQuestions();
            return questions;
        }

        @Override
        protected void onPostExecute(List<Question> questions) {
            super.onPostExecute(questions);

            mAdapter = new MyAdapter(getSupportFragmentManager(), questions);
            mPager = findViewById(R.id.viewPager1);
            mPager.setAdapter(mAdapter);

            quizData.close();
        }
    }


}

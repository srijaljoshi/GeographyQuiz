package edu.uga.cs6060.geographyquiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class QuizQuestionActivity extends AppCompatActivity {

    static final int NUM_ITEMS = 13;

    MyAdapter mAdapter;

    ViewPager mPager;

    List<Question> list;

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = findViewById(R.id.viewPager1);
        mPager.setAdapter(mAdapter);

        QuizData quizData = new QuizData(this);
        quizData.open();
        list = quizData.getQuestions();
        id = quizData.makeQuizEntry(list);


    }

    public static class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return QuizQuestionFragment.newInstance(position);
        }
    }


}

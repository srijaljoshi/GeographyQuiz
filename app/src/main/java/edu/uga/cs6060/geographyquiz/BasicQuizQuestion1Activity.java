package edu.uga.cs6060.geographyquiz;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BasicQuizQuestion1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_quiz_question);

        BasicQuizQuestionFragment basicQuizQuestionFragment = new BasicQuizQuestionFragment();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.linearLayout1, basicQuizQuestionFragment);
        ft.commit();
    }
}

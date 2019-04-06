package edu.uga.cs6060.geographyquiz;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;

public class SplashScreenActivity extends AppCompatActivity {

    Button startQuiz;
    Button viewResults;
    QuizData qd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Resources res = getResources();
        qd = new QuizData(this);

        qd.open();

        InputStream in_s =  res.openRawResource(R.raw.country_continent);
        qd.storeBasicQuestions(in_s);

        in_s = res.openRawResource(R.raw.country_neighbors);
        qd.storeBasicQuestions(in_s);

        startQuiz = findViewById( R.id.button1 );
        startQuiz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuizQuestionActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        viewResults = findViewById( R.id.button2 );
        viewResults.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PastResultsActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}

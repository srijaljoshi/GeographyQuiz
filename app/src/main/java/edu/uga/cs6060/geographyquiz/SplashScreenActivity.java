package edu.uga.cs6060.geographyquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SplashScreenActivity extends AppCompatActivity {

    Button startQuiz;
    Button viewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

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

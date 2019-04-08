package edu.uga.cs6060.geographyquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizResultsActivity extends AppCompatActivity {

    TextView quizResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        int quizScore = getIntent().getIntExtra("quizScore", 0);

        quizResults = findViewById(R.id.textView5);

        quizResults.setText(getString(R.string.quiz_results, Integer.toString(quizScore)));

        Button goHome = findViewById(R.id.button4);
        goHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SplashScreenActivity.class);
                v.getContext().startActivity(intent);
            }
        });

    }
}

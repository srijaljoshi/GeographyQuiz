package edu.uga.cs6060.geographyquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartQuizActivity extends AppCompatActivity {

    Button basicQuiz;
    Button advancedQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);

        basicQuiz = findViewById(R.id.button3);
        basicQuiz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BasicQuizQuestionActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        advancedQuiz = findViewById(R.id.button4);
        advancedQuiz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AdvancedQuizQuestionActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }


}

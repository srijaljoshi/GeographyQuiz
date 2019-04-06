package edu.uga.cs6060.geographyquiz;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;

public class SplashScreenActivity extends AppCompatActivity {

    private final static String TAG = "SplashScreenActivity";

    Button startQuiz;
    Button viewResults;

    QuizData quizData;
    InputStream in_s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Resources res = getResources();
        quizData = new QuizData(this);
        in_s =  res.openRawResource(R.raw.country_continent);

//        in_s = res.openRawResource(R.raw.country_neighbors);
//
//        qd.storeAdvancedQuestions(in_s);

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

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "SplashScreenActivity.onResume()" );
        if( quizData != null )
            quizData.open();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "SplashScreenActivity.onPause()" );
        if( quizData != null )
            quizData.close();

    }

    private class DBWriterTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            // Write the parameters to the database
            quizData.storeBasicQuestions(in_s);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}

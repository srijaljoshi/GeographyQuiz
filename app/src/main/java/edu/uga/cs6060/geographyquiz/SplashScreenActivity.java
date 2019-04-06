package edu.uga.cs6060.geographyquiz;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;

public class SplashScreenActivity extends AppCompatActivity {

    private final static String TAG = "SplashScreenActivity";

    Button startQuiz;
    Button viewResults;

    QuizData quizData;
    InputStream ins_basic;
    InputStream ins_advanced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Resources res = getResources();
        quizData = new QuizData(this);

        // Different inputstreams so that passing the argument is simpler
        ins_basic = res.openRawResource(R.raw.country_continent);
        ins_advanced = res.openRawResource(R.raw.country_neighbors);

        // open the db and getWritableDatabase
        quizData.open();

        // Call the aynctask to populate the database
        new DBWriterTask().execute();

        startQuiz = findViewById(R.id.button1);
        startQuiz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuizQuestionActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        viewResults = findViewById(R.id.button2);
        viewResults.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PastResultsActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    /**
     * @author Srijal Joshi
     * This inner class is used to populate the the countries and neighbors table aynchronously
     * Since we are not really passing any parameter or expecting any result, we set the
     * AsyncTask<Params, Progress, Results> to Void
     */
    private class DBWriterTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            // Write the parameters to the database
            quizData.storeBasicQuestions(ins_basic);
            quizData.storeAdvancedQuestions(ins_advanced);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            // Show a quick confirmation
//            Toast.makeText( getApplicationContext(), "Basic Questions created and populated!",
//                    Toast.LENGTH_SHORT).show();
        }
    }

}

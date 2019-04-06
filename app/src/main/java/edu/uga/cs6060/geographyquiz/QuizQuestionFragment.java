package edu.uga.cs6060.geographyquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Random;

import static android.content.ContentValues.TAG;


public class QuizQuestionFragment extends Fragment {

    int mNum;

    static QuizQuestionFragment newInstance(int num) {
        QuizQuestionFragment f = new QuizQuestionFragment();

        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = null;

        if(mNum == 12) {
            rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_submit_quiz, container, false);
            Button submitQuiz = rootView.findViewById(R.id.button3);
            submitQuiz.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), QuizResultsActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        } else {

            Random rand = new Random();
            int correctAnswer = rand.nextInt(3) + 1;

            if(mNum % 2 == 0) {
                rootView = (ViewGroup) inflater.inflate(
                        R.layout.fragment_quiz_question, container, false);

                //get random country
                //get correct continent country is in
                //store correct continent in db?
                //get 2 other random continents

                TextView textView = rootView.findViewById(R.id.textView3);
                textView.setText("What continent is Argentina in?");
                if(correctAnswer == 1) {
                    RadioButton choice1 = rootView.findViewById(R.id.radioButton1);
                    choice1.setText("A. Correct Continent");
                    RadioButton choice2 = rootView.findViewById(R.id.radioButton2);
                    choice2.setText("B. Random Continent");
                    RadioButton choice3 = rootView.findViewById(R.id.radioButton3);
                    choice3.setText("C. Random Continent");
                } else if (correctAnswer == 2) {
                    RadioButton choice1 = rootView.findViewById(R.id.radioButton1);
                    choice1.setText("A. Random Continent");
                    RadioButton choice2 = rootView.findViewById(R.id.radioButton2);
                    choice2.setText("B. Correct Continent");
                    RadioButton choice3 = rootView.findViewById(R.id.radioButton3);
                    choice3.setText("C. Random Continent");
                } else {
                    RadioButton choice1 = rootView.findViewById(R.id.radioButton1);
                    choice1.setText("A. Random Continent");
                    RadioButton choice2 = rootView.findViewById(R.id.radioButton2);
                    choice2.setText("B. Random Continent");
                    RadioButton choice3 = rootView.findViewById(R.id.radioButton3);
                    choice3.setText("C. Correct Continent");
                }
            } else {
                rootView = (ViewGroup) inflater.inflate(
                        R.layout.fragment_quiz_question, container, false);

                //get random country
                //get correct country that neighbors it
                //store correct country in db?
                //get 2 other random countries that DO NOT neighbor it

                TextView textView = rootView.findViewById(R.id.textView3);
                textView.setText("Which country neighbors the United States?");
                if(correctAnswer == 1) {
                    RadioButton choice1 = rootView.findViewById(R.id.radioButton1);
                    choice1.setText("A. Correct Country");
                    RadioButton choice2 = rootView.findViewById(R.id.radioButton2);
                    choice2.setText("B. Random Country");
                    RadioButton choice3 = rootView.findViewById(R.id.radioButton3);
                    choice3.setText("C. Random Country");
                } else if (correctAnswer == 2) {
                    RadioButton choice1 = rootView.findViewById(R.id.radioButton1);
                    choice1.setText("A. Random Country");
                    RadioButton choice2 = rootView.findViewById(R.id.radioButton2);
                    choice2.setText("B. Correct Country");
                    RadioButton choice3 = rootView.findViewById(R.id.radioButton3);
                    choice3.setText("C. Random Country");
                } else {
                    RadioButton choice1 = rootView.findViewById(R.id.radioButton1);
                    choice1.setText("A. Random Country");
                    RadioButton choice2 = rootView.findViewById(R.id.radioButton2);
                    choice2.setText("B. Random Country");
                    RadioButton choice3 = rootView.findViewById(R.id.radioButton3);
                    choice3.setText("C. Correct Country");
                }
            }

            RadioGroup radioGroup = rootView.findViewById(R.id.radioGroup1);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    //write user selection to database?
                }
            });
        }

        return rootView;
    }

}

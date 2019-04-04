package edu.uga.cs6060.geographyquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;


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
            if(mNum % 2 == 0) {
                rootView = (ViewGroup) inflater.inflate(
                        R.layout.fragment_quiz_question, container, false);
                TextView textView = rootView.findViewById(R.id.textView3);
                textView.setText("What continent is Argentina in?");
                RadioButton choice1 = rootView.findViewById(R.id.radioButton1);
                choice1.setText("A. North America");
                RadioButton choice2 = rootView.findViewById(R.id.radioButton2);
                choice2.setText("B. Africa");
                RadioButton choice3 = rootView.findViewById(R.id.radioButton3);
                choice3.setText("C. South America");

            } else {
                rootView = (ViewGroup) inflater.inflate(
                        R.layout.fragment_quiz_question, container, false);
                TextView textView = rootView.findViewById(R.id.textView3);
                textView.setText("What countries neighbor the United States?");
                RadioButton choice1 = rootView.findViewById(R.id.radioButton1);
                choice1.setText("A. Mexico");
                RadioButton choice2 = rootView.findViewById(R.id.radioButton2);
                choice2.setText("B. France");
                RadioButton choice3 = rootView.findViewById(R.id.radioButton3);
                choice3.setText("C. Russia");
            }


        }

        return rootView;
    }

}

package edu.uga.cs6060.geographyquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;


public class QuizQuestionFragment extends Fragment {

    int mNum;
    static List<Question> list;

    static QuizQuestionFragment newInstance(int num, List<Question> questionList) {
        QuizQuestionFragment f = new QuizQuestionFragment();

        list = questionList;

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
                        R.layout.fragment_basic_question, container, false);

                TextView textView = rootView.findViewById(R.id.textView3);
                textView.setText(getString(R.string.basic_question, list.get(mNum/2).getCountry()));
                if(correctAnswer == 1) {
                    RadioButton choice1 = rootView.findViewById(R.id.radioButton1);
                    choice1.setText(getString(R.string.choice1, list.get(mNum/2).getContinent_answer()));
                    RadioButton choice2 = rootView.findViewById(R.id.radioButton2);
                    choice2.setText(getString(R.string.choice2, list.get(mNum/2).getWrong_continent_1()));
                    RadioButton choice3 = rootView.findViewById(R.id.radioButton3);
                    choice3.setText(getString(R.string.choice3, list.get(mNum/2).getWrong_continent_2()));
                } else if (correctAnswer == 2) {
                    RadioButton choice1 = rootView.findViewById(R.id.radioButton1);
                    choice1.setText(getString(R.string.choice1, list.get(mNum/2).getWrong_continent_1()));
                    RadioButton choice2 = rootView.findViewById(R.id.radioButton2);
                    choice2.setText(getString(R.string.choice2, list.get(mNum/2).getContinent_answer()));
                    RadioButton choice3 = rootView.findViewById(R.id.radioButton3);
                    choice3.setText(getString(R.string.choice3, list.get(mNum/2).getWrong_continent_2()));
                } else {
                    RadioButton choice1 = rootView.findViewById(R.id.radioButton1);
                    choice1.setText(getString(R.string.choice1, list.get(mNum/2).getWrong_continent_1()));
                    RadioButton choice2 = rootView.findViewById(R.id.radioButton2);
                    choice2.setText(getString(R.string.choice2, list.get(mNum/2).getWrong_continent_2()));
                    RadioButton choice3 = rootView.findViewById(R.id.radioButton3);
                    choice3.setText(getString(R.string.choice3, list.get(mNum/2).getContinent_answer()));
                }
            } else {
                rootView = (ViewGroup) inflater.inflate(
                        R.layout.fragment_advanced_question, container, false);

                TextView textView = rootView.findViewById(R.id.textView4);
                textView.setText(getString(R.string.advanced_question, list.get(mNum/2).getCountry()));
                if(correctAnswer == 1) {
                    RadioButton choice1 = rootView.findViewById(R.id.radioButton4);
                    choice1.setText(getString(R.string.choice1, list.get(mNum/2).getNeighbor_answer()));
                    RadioButton choice2 = rootView.findViewById(R.id.radioButton5);
                    choice2.setText(getString(R.string.choice2, list.get(mNum/2).getWrong_neighbor_1()));
                    RadioButton choice3 = rootView.findViewById(R.id.radioButton6);
                    choice3.setText(getString(R.string.choice3, list.get(mNum/2).getWrong_neighbor_2()));
                } else if (correctAnswer == 2) {
                    RadioButton choice1 = rootView.findViewById(R.id.radioButton4);
                    choice1.setText(getString(R.string.choice1, list.get(mNum/2).getWrong_neighbor_1()));
                    RadioButton choice2 = rootView.findViewById(R.id.radioButton5);
                    choice2.setText(getString(R.string.choice2, list.get(mNum/2).getNeighbor_answer()));
                    RadioButton choice3 = rootView.findViewById(R.id.radioButton6);
                    choice3.setText(getString(R.string.choice3, list.get(mNum/2).getWrong_neighbor_2()));
                } else {
                    RadioButton choice1 = rootView.findViewById(R.id.radioButton4);
                    choice1.setText(getString(R.string.choice1, list.get(mNum/2).getWrong_neighbor_1()));
                    RadioButton choice2 = rootView.findViewById(R.id.radioButton5);
                    choice2.setText(getString(R.string.choice2, list.get(mNum/2).getWrong_neighbor_2()));
                    RadioButton choice3 = rootView.findViewById(R.id.radioButton6);
                    choice3.setText(getString(R.string.choice3, list.get(mNum/2).getNeighbor_answer()));
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

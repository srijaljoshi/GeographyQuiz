package edu.uga.cs6060.geographyquiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                    R.layout.fragment_quiz_results, container, false);
        } else {
             rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_quiz_question, container, false);
//             TextView textView = getView().findViewById(R.id.textView3);
//             textView.setText("THIS IS NEW TEXT");
        }

        return rootView;
    }

}

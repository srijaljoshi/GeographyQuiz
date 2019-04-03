package edu.uga.cs6060.geographyquiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class QuizQuestionFragment extends Fragment {

    int mNum;
    boolean isBasic;

    static QuizQuestionFragment newInstance(int num, boolean isBasic) {
        QuizQuestionFragment f = new QuizQuestionFragment();

        Bundle args = new Bundle();
        args.putInt("num", num);
        args.putBoolean("isBasic", isBasic);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 0;
        isBasic = getArguments() != null ? getArguments().getBoolean("isBasic") : true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = null;

        if(mNum == 6  && isBasic || mNum == 12 && !isBasic) {
            rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_quiz_results, container, false);
        } else {
             rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_quiz_question, container, false);
        }

        return rootView;
    }

}

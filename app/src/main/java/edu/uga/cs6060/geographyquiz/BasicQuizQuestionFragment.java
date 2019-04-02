package edu.uga.cs6060.geographyquiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BasicQuizQuestionFragment extends Fragment {

    int mNum;

    static BasicQuizQuestionFragment newInstance(int num) {
        BasicQuizQuestionFragment f = new BasicQuizQuestionFragment();

        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = null;

        if(mNum == 6) {
            rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_quiz_results, container, false);
        } else {
             rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_basic_quiz_question, container, false);
        }

        return rootView;
    }

}

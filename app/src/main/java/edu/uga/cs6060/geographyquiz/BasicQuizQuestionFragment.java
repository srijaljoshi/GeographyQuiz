package edu.uga.cs6060.geographyquiz;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class BasicQuizQuestionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_basic_quiz_question, null);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        LinearLayout linearLayout;
        linearLayout = view.findViewById(R.id.linearLayout1);

        TextView question = new TextView(getActivity());
        question.setText("1. What continent is Argentina in?");
        question.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        question.setTypeface(null, Typeface.BOLD);
        question.setGravity(Gravity.CENTER_HORIZONTAL);

        RadioButton[] rb = new RadioButton[3];
        RadioGroup rg = new RadioGroup(getActivity());
        rg.setOrientation(RadioGroup.VERTICAL);
        rg.setGravity(Gravity.CENTER_HORIZONTAL);

        int dpValue = 35; // margin in dps
        float d = Resources.getSystem().getDisplayMetrics().density;
        int viewMargins = (int)(dpValue * d); // margin in pixels

        LinearLayout.LayoutParams rgParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        rgParams.setMargins(0,viewMargins,0,0);
        rg.setLayoutParams(rgParams);

        for(int i=0; i < rb.length; i++){
            rb[i]  = new RadioButton(getActivity());
            rg.addView(rb[i]);
            rb[i].setText("Test");
            rb[i].setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        }

        try{
            linearLayout.addView(question);
            linearLayout.addView(rg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

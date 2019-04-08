package edu.uga.cs6060.geographyquiz;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * @author Srijal Joshi
 * This activity uses RecyclerView to display the past results
 */

public class PastResultsActivity extends AppCompatActivity {

    private static final String TAG = "PastResultsActivity";
    private QuizData quizData;
    private List<Quiz> mQuizList;

    /**
     * Implementing a RecyclerView requires the following steps:
     *
     * Add the RecyclerView dependency if needed (depending on which template is used for the Activity).
     * Add the RecyclerView to the Activity layout.
     * Create a layout XML file for one View item.
     * Extend RecyclerView.Adapter and implement the onCreateViewHolder() and onBindViewHolder() methods.
     * Extend RecyclerView.ViewHolder to create a ViewHolder for your item layout. You can add click behavior by overriding the onClick() method.
     * In the Activity, inside the onCreate() method, create a RecyclerView and initialize it with the adapter and a layout manager.
     */
    // 1. Declare a RecyclerView.
    private RecyclerView mRecyclerView;
    private ResultRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_results);

        quizData = new QuizData(this);

        context = getApplicationContext();
        // set recyclerview inside asynctask
        new AsyncQuizResultsTask().execute();

    }

    public void logQuizzes() {
        for (Quiz quiz : mQuizList) {
                Log.d(TAG, "Quiz: " + quiz);
        }
    }


    private class ResultRecyclerAdapter extends RecyclerView.Adapter<ResultRecyclerAdapter.ResultViewHolder> {

        List<Quiz> recyclerQuizList;
        LayoutInflater mInflater;

        public ResultRecyclerAdapter(Context context, List<Quiz> quizList) {
            mInflater = LayoutInflater.from(context);
            recyclerQuizList = quizList;
        }

        /**
         * onCreateViewHolder() creates a View and returns it.
         * @param parent
         * @param i
         * @return
         */
        @NonNull
        @Override
        public ResultRecyclerAdapter.ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = mInflater.inflate(R.layout.result_recycler_layout, parent, false);
            return new ResultViewHolder(view);
        }

        /**
         * onBindViewHolder() associates the data with the ViewHolder for a given position in the RecyclerView.
         * @param resultViewHolder
         * @param position
         */
        @Override
        public void onBindViewHolder(@NonNull ResultRecyclerAdapter.ResultViewHolder resultViewHolder, int position) {
            // Retrieve the data for that position
            Quiz mCurrentQuizResult = recyclerQuizList.get(position);
            // Add the data to the view
            Log.d( TAG, "onBindViewHolder: " + mCurrentQuizResult );

            resultViewHolder.tvQuizId.setText( String.valueOf(mCurrentQuizResult.get_id()));
            resultViewHolder.tvQuizDate.setText( mCurrentQuizResult.getDate());
            resultViewHolder.tvQuizResult.setText( String.valueOf(mCurrentQuizResult.getResult()));
        }

        /**
         *  returns the number of data items available for displaying.
         * @return
         */
        @Override
        public int getItemCount() {
            return recyclerQuizList.size();
        }


        class ResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView tvQuizId;
            TextView tvQuizDate;
            TextView tvQuizResult;

            public ResultViewHolder(@NonNull View itemView) {
                super(itemView);


                tvQuizId= (TextView) itemView.findViewById( R.id.tvQuizId);
                tvQuizDate = (TextView) itemView.findViewById( R.id.tvQuizDate);
                tvQuizResult = (TextView) itemView.findViewById( R.id.tvQuizResult);

            }

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Good luck next time!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class AsyncQuizResultsTask extends AsyncTask<Void, Void, List<Quiz>> {

        @Override
        protected List<Quiz> doInBackground(Void... voids) {
            quizData.open();
            mQuizList = quizData.getQuizResults();
            return mQuizList;
        }

        @Override
        protected void onPostExecute(List<Quiz> mQuizList) {
            super.onPostExecute(mQuizList);
            logQuizzes();

            // 2. get a handle to the RecyclerView in the layout:
            mRecyclerView = findViewById(R.id.recyclerView);

            // 3. Create an adapter and supply the data to be displayed.
            mAdapter = new ResultRecyclerAdapter(context, mQuizList);
            mRecyclerView.setAdapter(mAdapter);

            // 4. Connect the adapter with the RecyclerView.
            mLayoutManager = new LinearLayoutManager(context);

            // 5. Give the RecyclerView a default layout manager.
            mRecyclerView.setLayoutManager(mLayoutManager);
        }
    }

}

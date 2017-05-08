package com.rodrigoftw.myworkouttracker.myworkouttracker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rodrigoftw.myworkouttracker.myworkouttracker.R;
import com.rodrigoftw.myworkouttracker.myworkouttracker.model.Exercise;

import java.util.ArrayList;

/**
 * Created by Rodrigo on 07/05/2017.
 */

public class SimpleExerciseAdapter extends RecyclerView.Adapter<SimpleExerciseAdapter.SimpleExerciseViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<Exercise> mData = new ArrayList<Exercise>();
    private Context ctx;

    public SimpleExerciseAdapter(Context ctx, ArrayList<Exercise> mData) {
        this.ctx = ctx;
        this.mData = mData;
        mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public SimpleExerciseAdapter.SimpleExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_simple_exercise_item, parent, false);
        SimpleExerciseAdapter.SimpleExerciseViewHolder holder = new SimpleExerciseAdapter.SimpleExerciseViewHolder(view);
        return holder;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(SimpleExerciseAdapter.SimpleExerciseViewHolder holder, final int position) {
        Exercise exercise = mData.get(position);

        holder.nameExercise.setText(exercise.getNameExercise());
        /*holder.setsExercise.setText(exercise.getSetsExercise());
        holder.repsExercise.setText(exercise.getRepsExercise());
        holder.restExercise.setText(exercise.getRestExercise());*/
    }


    public static class SimpleExerciseViewHolder extends RecyclerView.ViewHolder {
        public TextView nameExercise;
        /*public TextView setsExercise;
        public TextView repsExercise;
        public TextView restExercise;*/

        private View view;
        public SimpleExerciseViewHolder(View view) {
            super(view);
            this.view = view;

            nameExercise = (TextView) view.findViewById(R.id.exerciseName);
            /*setsExercise = (TextView) view.findViewById(R.id.exerciseSets);
            repsExercise = (TextView) view.findViewById(R.id.exerciseReps);
            restExercise = (TextView) view.findViewById(R.id.exerciseRest);*/

        }
    }
}

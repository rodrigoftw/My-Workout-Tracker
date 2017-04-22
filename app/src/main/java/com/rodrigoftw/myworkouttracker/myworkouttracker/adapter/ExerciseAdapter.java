package com.rodrigoftw.myworkouttracker.myworkouttracker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rodrigoftw.myworkouttracker.myworkouttracker.R;
import com.rodrigoftw.myworkouttracker.myworkouttracker.model.Exercise;

import java.util.ArrayList;

/**
 * Created by Rodrigo on 14/04/2017.
 */

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<Exercise> mData = new ArrayList<Exercise>();
    private Context ctx;

    public ExerciseAdapter(Context ctx, ArrayList<Exercise> mData) {
        this.ctx = ctx;
        this.mData = mData;
        mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ExerciseAdapter.ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_exercise_item, parent, false);
        ExerciseAdapter.ExerciseViewHolder holder = new ExerciseAdapter.ExerciseViewHolder(view);
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
    public void onBindViewHolder(ExerciseAdapter.ExerciseViewHolder holder, final int position) {
        Exercise exercise = mData.get(position);

        Glide.clear(holder.imageExercise);
        Glide.with(ctx)
                .load(exercise.getImageExercise())
                .error( R.layout.layout_missing_exercise_image )
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imageExercise);

        holder.nameExercise.setText(exercise.getNameExercise());
        /*holder.setsExercise.setText(exercise.getSetsExercise());*/
        /*holder.repsExercise.setText(exercise.getRepsExercise());*/
        holder.restExercise.setText(exercise.getRestExercise());
    }


    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageExercise;
        public TextView nameExercise;
        public TextView setsExercise;
        public TextView repsExercise;
        public TextView restExercise;

        private View view;
        public ExerciseViewHolder(View view) {
            super(view);
            this.view = view;

            imageExercise = (ImageView) view.findViewById(R.id.exerciseImage);
            nameExercise = (TextView) view.findViewById(R.id.nameExercise);
            setsExercise = (TextView) view.findViewById(R.id.setsExercise);
            repsExercise = (TextView) view.findViewById(R.id.repsExercise);
            restExercise = (TextView) view.findViewById(R.id.restExercise);

        }
    }
}

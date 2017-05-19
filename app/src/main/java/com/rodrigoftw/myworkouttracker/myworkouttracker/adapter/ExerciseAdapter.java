package com.rodrigoftw.myworkouttracker.myworkouttracker.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

    private int time = 1;
    private static final int INCREMENTTIME = 1;
    private static final int MINTIME = 1;
    private static final int MAXTIME = 10;
    private boolean longClick = false;
    Handler handler = new Handler();

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
    public void onBindViewHolder(final ExerciseAdapter.ExerciseViewHolder holder, final int position) {
        Exercise exercise = mData.get(position);

        Glide.clear(holder.imageExercise);
        Glide.with(ctx)
                .load(exercise.getImageExercise())
                .error( R.drawable.icon_error )
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imageExercise);

        holder.nameExercise.setText(exercise.getNameExercise());
        holder.setsExercise.setText(String.valueOf(exercise.getSetsExercise()));
        holder.repsExercise.setText(String.valueOf(exercise.getRepsExercise()));
        holder.restExercise.setText(String.valueOf(exercise.getRestExercise()) + " segundos");
        holder.startExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exerciseAlertDialog(ctx);
            }
        });

        /*new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                holder.remainingRestTime.setText((int) (millisUntilFinished / 1000));
            }

            public void onFinish() {
                holder.remainingRestTime.setText("done!");
            }
        }.start();*/

        /*holder.stepperTouch.getStepper().setMin(1);
        holder.stepperTouch.getStepper().setMax(5);
        holder.stepperTouch.getStepper().addStepCallback((new OnStepCallback() {
            public void onStep(int value, boolean positive) {
                //  mFanRef.setValue(value);
            }
        }));*/


        //holder.numberSet.setText(String.valueOf(time) + "ª série");

        /*holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (longClick) {
                    longClick = false;
                    handler.removeCallbacks(updateMinusSet);
                    return;
                }
                decrementTime();
            }
        });

        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (longClick) {
                    longClick = false;
                    handler.removeCallbacks(updatePlusSet);
                    return;
                }
                incrementTime();
            }
        });*/

        /*holder.minusButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClick = true;
                handler.postDelayed(updateMinusSet, 0);
                return false;
            }
        });

        holder.plusButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClick = true;
                handler.postDelayed(updatePlusSet, 0);
                return false;
            }
        });*/
    }

    private Runnable updateMinusSet = new Runnable(){
        public void run(){
            decrementTime();
            handler.postDelayed(updateMinusSet, 400);
        }
    };

    private Runnable updatePlusSet = new Runnable(){
        public void run(){
            incrementTime();
            handler.postDelayed(updatePlusSet, 400);
        }
    };

    private void updateTime(){
        //holder.numberSet.setText(String.valueOf(time) + "ª série");
    }

    private void incrementTime() {
        if(time < MAXTIME) {
            time += INCREMENTTIME;
            updateTime();

        }
    }

    private void decrementTime() {
        if(time > MINTIME) {
            time -= INCREMENTTIME;
            updateTime();
        }
    }

    /*private void restTimeCountdown() {
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                remainingRestTime.setText(millisUntilFinished / 1000);
            }

            public void onFinish() {
                remainingRestTime.setText("done!");
            }
        }.start();
    }*/

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageExercise;
        public TextView nameExercise;
        public TextView setsExercise;
        public TextView repsExercise;
        public TextView restExercise;
        public RelativeLayout startExerciseButton;
        public AppCompatButton plusButton;
        public AppCompatButton minusButton;
        public TextView numberSet;
        public TextView remainingRestTime;
        /*private StepperTouch stepperTouch;*/

        private View view;
        public ExerciseViewHolder(View view) {
            super(view);
            this.view = view;

            imageExercise = (ImageView) view.findViewById(R.id.exerciseImage);
            nameExercise = (TextView) view.findViewById(R.id.nameExercise);
            setsExercise = (TextView) view.findViewById(R.id.setsExercise);
            repsExercise = (TextView) view.findViewById(R.id.repsExercise);
            restExercise = (TextView) view.findViewById(R.id.restExercise);
            startExerciseButton = (RelativeLayout) view.findViewById(R.id.startExerciseButton);
            minusButton = (AppCompatButton) view.findViewById(R.id.minusButton);
            numberSet = (TextView) view.findViewById(R.id.numberSet);
            plusButton = (AppCompatButton) view.findViewById(R.id.plusButton);
            remainingRestTime = (TextView) view.findViewById(R.id.remainingRestTime);
            /*stepperTouch = (StepperTouch) view.findViewById(R.id.stepperTouch);*/
        }
    }

    private void exerciseAlertDialog(final Context ctx) {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ctx, R.style.AlertDialogCustom));
        builder.setTitle("")//"Login inválido!")
                .setView(R.layout.exercise_alert_dialog)
                .setCancelable(false)
                //.setMessage("O e-mail ou a senha inseridos não foram encontrados, tente novamente.")
                .setPositiveButton("Finalizar série e descansar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        restAlertDialog(ctx);
                        //dialog.dismiss();
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();

    }

    private void restAlertDialog(final Context ctx) {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ctx, R.style.AlertDialogCustom));
        builder.setTitle("")
                .setView(R.layout.rest_alert_dialog)
                .setCancelable(false)
                //.setMessage("O e-mail ou a senha inseridos não foram encontrados, tente novamente.")
                .setPositiveButton("Próxima série", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /*restTimeCountdown();*/
                        dialog.dismiss();
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();

    }
}

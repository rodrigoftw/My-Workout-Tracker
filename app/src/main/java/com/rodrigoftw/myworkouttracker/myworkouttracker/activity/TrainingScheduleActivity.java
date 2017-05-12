package com.rodrigoftw.myworkouttracker.myworkouttracker.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.rodrigoftw.myworkouttracker.myworkouttracker.R;
import com.rodrigoftw.myworkouttracker.myworkouttracker.adapter.ExerciseAdapter;
import com.rodrigoftw.myworkouttracker.myworkouttracker.model.Exercise;

import java.util.ArrayList;
import java.util.Date;

import static com.rodrigoftw.myworkouttracker.myworkouttracker.R.id.currentTrainingDateTextView;

public class TrainingScheduleActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String DATE_TEMPLATE = "dd/MM/yyyy";

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, TrainingScheduleActivity.class));
    }

    RecyclerView recyclerView;
    ExerciseAdapter adapter;
    TextView trainingDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        setTitle(R.string.home_title);

        this.ctx = this;

        firebaseAuth = firebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            Log.i("verifyUser", "Usuário logado");
        } else {
            Log.i("verifyUser", "Usuário não logado");
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewExercise);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        trainingDate = (TextView) findViewById(currentTrainingDateTextView);
        trainingDate.setText(String.format("%s - Treino de Peitoral/Dorsal", formatDate(DATE_TEMPLATE, new Date(System.currentTimeMillis()))));

        final ArrayList<Exercise> exercise = new ArrayList<>();

        // add items to list
        /*for (int i = 0; i < 8; i++) {
            exercise.add(
                new Exercise(i, "Supino Reto com Barra", 3, 10, "60 segundos", "http://www.exrx.net/AnimatedEx/PectoralSternal/BBBenchPress.gif")
            );
        }*/

        exercise.add(
                new Exercise(1, "Supino Reto com Barra", 3, 10, "60 segundos", "http://www.exrx.net/AnimatedEx/PectoralSternal/BBBenchPress.gif")
        );
        exercise.add(
                new Exercise(2, "Supino Inclinado com Barra", 3, 10, "60 segundos", "http://www.exrx.net/AnimatedEx/PectoralClavicular/BBInclineBenchPress.gif")
        );
        exercise.add(
                new Exercise(3, "Supino Declinado com Barra", 3, 10, "60 segundos", "http://www.exrx.net/AnimatedEx/PectoralSternal/BBDeclineBenchPress.gif")
        );
        exercise.add(
                new Exercise(4, "Crossover", 3, 10, "60 segundos", "http://www.exrx.net/AnimatedEx/PectoralSternal/CBStandingFly.gif")
        );
        exercise.add(
                new Exercise(5, "Barra Fixa", 4, 8, "90 segundos", "http://www.exrx.net/AnimatedEx/LatissimusDorsi/WTChinUp.gif")
        );
        exercise.add(
                new Exercise(6, "Puxada Frontal", 3, 10, "60 segundos", "http://www.exrx.net/AnimatedEx/LatissimusDorsi/CBFrontPulldown.gif")
        );
        exercise.add(
                new Exercise(7, "Puxada Traseira", 3, 10, "60 segundos", "http://www.exrx.net/AnimatedEx/LatissimusDorsi/CBRearPulldown.gif")
        );
        exercise.add(
                new Exercise(8, "Remada Sentada", 3, 12, "60 segundos", "http://www.exrx.net/AnimatedEx/BackGeneral/CBSeatedRowStraightBack.gif")
        );

        adapter = new ExerciseAdapter(ctx, exercise);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        /*ImageView imageview = (ImageView) findViewById(R.id.exerciseImage);

        Glide.with(getApplicationContext())
                .load("https://www.bodybuilding.com/exercises/exerciseImages/sequences/360/Male/l/360_1.jpg")*//*https://www.bodybuilding.com/exercises/exerciseImages/sequences/360/Male/l/360_1.jpg")*//*
                *//*.placeholder(R.drawable.gif1benchpress)*//*
                *//*.crossFade()*//*
                .into(imageview);*/

        /*Glide.with(ctx)
                .load("http://www.exrx.net/AnimatedEx/PectoralSternal/BBBenchPress.gif")
                .asGif()
                .placeholder(R.drawable.gif1benchpress)
                .crossFade()
                .into(imageview);*/

       /* Glide
            .with(getApplicationContext())
            .load("http://www.exrx.net/AnimatedEx/PectoralSternal/BBBenchPress.gif")
            .listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            })
            .into(imageview);*/

        /*final List<ItemModel> data = new ArrayList<>();
        data.add(new ItemModel(
                "1 - Supino Reto com Barra",
                R.color.material_blue_500,
                R.color.material_blue_300,
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));

        *//*GifView pGif = (GifView) findViewById(R.id.exerciseImage);
        pGif.setImageResource(R.drawable.gif1benchpress);*//*

        *//*ImageView imageView = (ImageView) findViewById(R.id.exerciseImage);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.drawable.gif1benchpress).into(imageViewTarget);*//*

                *//*ImageView image = (ImageView)findViewById(R.id.exerciseImage);
                if (image != null){
                    GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(image);
                    Glide.with(this).load("http://www.exrx.net/AnimatedEx/PectoralSternal/BBBenchPress.gif").into(imageViewTarget);
                }*//*
                //Glide.with(this).load(R.raw.sample_gif).into(imageViewTarget);
                //Glide.with(activity).load(url).asGif().into(view);;


        data.add(new ItemModel(
                "2 - Supino Inclinado com Barra",
                R.color.material_blue_500,
                R.color.material_blue_300,
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));

        data.add(new ItemModel(
                "3 - Supino Declinado com Barra",
                R.color.material_blue_500,
                R.color.material_blue_300,
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));
        data.add(new ItemModel(
                "4 - Crossover",
                R.color.material_blue_500,
                R.color.material_blue_300,
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));
        data.add(new ItemModel(
                "5 - Barra Fixa",
                R.color.material_blue_500,
                R.color.material_blue_300,
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));
        data.add(new ItemModel(
                "6 - Puxada Frontal",
                R.color.material_blue_500,
                R.color.material_blue_300,
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));
        data.add(new ItemModel(
                "7 - Puxada Traseira",
                R.color.material_blue_500,
                R.color.material_blue_300,
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));
        data.add(new ItemModel(
                "8 - Remada Sentada",
                R.color.material_blue_500,
                R.color.material_blue_300,
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));
        recyclerView.setAdapter(new ItemModelAdapter(data));*/
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_schedule) {
            
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(TrainingScheduleActivity.this, HistoryActivity.class));
            /*finish();*/
        } else if (id == R.id.nav_calendar) {
            startActivity(new Intent(TrainingScheduleActivity.this, CalendarActivity.class));
            /*finish();*/
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(TrainingScheduleActivity.this, UserDataActivity.class));
            /*finish();*/
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(TrainingScheduleActivity.this, HelpActivity.class));
            /*finish();*/
        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_logout) {
            logOutDialog(ctx);
            /*firebaseAuth.signOut();*/
            /*startActivity(new Intent(TrainingScheduleActivity.this, LoginActivity.class));
            finish();*/
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOutDialog(final Context ctx) {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ctx, R.style.AlertDialogCustom));
        builder.setTitle("Tem certeza de que deseja sair?")
                //.setMessage("O e-mail ou a senha inseridos não foram encontrados, tente novamente.")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(ctx, LoginActivity.class));
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }
}

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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import com.rodrigoftw.myworkouttracker.myworkouttracker.R;
import com.rodrigoftw.myworkouttracker.myworkouttracker.adapter.SimpleExerciseAdapter;
import com.rodrigoftw.myworkouttracker.myworkouttracker.component.LoadLayout;
import com.rodrigoftw.myworkouttracker.myworkouttracker.model.Exercise;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Date;

import static com.rodrigoftw.myworkouttracker.myworkouttracker.R.id.calendar_view;
import static com.rodrigoftw.myworkouttracker.myworkouttracker.util.AnimationUtils.animate;


public class CalendarActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, CalendarView.OnDateChangeListener {

    private static final String DATE_TEMPLATE = "dd/MM/yyyy";
    private static final String MONTH_TEMPLATE = "MMMM yyyy";
    CalendarView calendar = null;
    TextView trainingDate;
    TextView trainingType;
    private RecyclerView exerciseRecyclerView;
    private SimpleExerciseAdapter adapter;
    public LoadLayout loadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        setTitle(R.string.calendar_title);

        this.ctx = this;

        firebaseAuth = firebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            Log.i("verifyUser", "Usuário logado");
        } else {
            Log.i("verifyUser", "Usuário não logado");
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        calendar = (CalendarView) findViewById(calendar_view);
        calendar.setOnDateChangeListener(this);
        calendar.setFirstDayOfWeek(2);
        calendar.setShowWeekNumber(false);

        trainingDate = (TextView) findViewById(R.id.trainingDate);
        //trainingDate.setText(String.format("%s - Treino de Peitoral/Dorsal", formatDate(DATE_TEMPLATE, new Date(System.currentTimeMillis()))));
        trainingDate.setText(String.format("%s", formatDate(DATE_TEMPLATE, new Date(System.currentTimeMillis()))));

        animate(trainingDate, getApplicationContext());

        exerciseRecyclerView = (RecyclerView) findViewById(R.id.exercisesList);
        exerciseRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());

        // create list
        final ArrayList<Exercise> exercisesList = new ArrayList<Exercise>();

        // add items to list
        /*for (int i = 0; i < 5; i++) {
            exercisesList.add(
                    new Exercise(i, "Supino Reto com Barra", 3, 12, "60")
            );
        }*/

        exercisesList.add(
                new Exercise(1, "Supino Reto com Barra", 3, 10, 60)
        );
        exercisesList.add(
                new Exercise(2, "Supino Inclinado com Barra", 3, 10, 60)
        );
        exercisesList.add(
                new Exercise(3, "Supino Declinado com Barra", 3, 10, 60)
        );
        exercisesList.add(
                new Exercise(4, "Crossover", 3, 10, 60)
        );
        exercisesList.add(
                new Exercise(5, "Barra Fixa", 4, 8, 90)
        );
        exercisesList.add(
                new Exercise(6, "Puxada Frontal", 3, 10, 60)
        );
        exercisesList.add(
                new Exercise(7, "Puxada Traseira", 3, 10, 60)
        );
        exercisesList.add(
                new Exercise(8, "Remada Sentada", 3, 12, 60)
        );

        adapter = new SimpleExerciseAdapter(ctx, exercisesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        exerciseRecyclerView.setLayoutManager(mLayoutManager);
        exerciseRecyclerView.setAdapter(adapter);
        loadLayout = (LoadLayout) findViewById(R.id.loadLayout);

    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year, int monthOfYear, int dayOfMonth) {

        /*Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);*/

        /*Date date=new Date();
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        c = df2.format(date);*/
        /*String b = null;

        if (monthOfYear <= 9) {
            b = "0" + String.valueOf(monthOfYear + 1);
        } else *//*if (monthOfYear > 10)*//*{
            b = String.valueOf(monthOfYear + 1);
        }*//* else if (monthOfYear == 10) {
            b = String.valueOf(monthOfYear + 1);
        }*/

        /*trainingDate.setText("");
        String c = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year; *//*+ " - " + dayOfWeek;*//* *//*" - Treino de Peitoral/Dorsal";*//*
        trainingDate.append(c);*/

        trainingDate = (TextView) findViewById(R.id.trainingDate);
        trainingDate.setText(String.format("%s - Treino de Peitoral/Dorsal", formatDate(DATE_TEMPLATE, new Date(year - 1900, monthOfYear, dayOfMonth))));

        animate(trainingDate, getApplicationContext());

        /*Calendar then = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        Toast.makeText(this, then.getTime().toString(), Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } /*else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_schedule) {
            startActivity(new Intent(ctx,TrainingScheduleActivity.class));
            /*finish();*/
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(ctx, HistoryActivity.class));
            /*finish();*/
        } else if (id == R.id.nav_calendar) {

        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(ctx, UserDataActivity.class));
            /*finish();*/
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(ctx, HelpActivity.class));
            /*finish();*/
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(ctx, AboutActivity.class));
        } else if (id == R.id.nav_logout) {
            logOutDialog(ctx);
            /*firebaseAuth.signOut();*/
            /*startActivity(new Intent(ctx, LoginActivity.class));
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

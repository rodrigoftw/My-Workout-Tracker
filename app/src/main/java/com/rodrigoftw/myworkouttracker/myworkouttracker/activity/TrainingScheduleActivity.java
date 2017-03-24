package com.rodrigoftw.myworkouttracker.myworkouttracker.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.github.aakira.expandablelayout.Utils;
import com.rodrigoftw.myworkouttracker.myworkouttracker.R;
import com.rodrigoftw.myworkouttracker.myworkouttracker.adapter.ExerciseAdapter;
import com.rodrigoftw.myworkouttracker.myworkouttracker.model.ItemModel;
import com.rodrigoftw.myworkouttracker.myworkouttracker.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TrainingScheduleActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, TrainingScheduleActivity.class));
    }

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        setTitle(R.string.home_title);

        this.ctx = this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageView imageview = (ImageView) findViewById(R.id.exerciseImage);

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

        final List<ItemModel> data = new ArrayList<>();
        data.add(new ItemModel(
                "1 - Supino Reto com Barra",
                R.color.material_blue_500,
                R.color.material_blue_300,
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));

        /*ImageView imageView = (ImageView) findViewById(R.id.exerciseImage);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.drawable.gif1benchpress).into(imageViewTarget);*/

                /*ImageView image = (ImageView)findViewById(R.id.exerciseImage);
                if (image != null){
                    GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(image);
                    Glide.with(this).load("http://www.exrx.net/AnimatedEx/PectoralSternal/BBBenchPress.gif").into(imageViewTarget);
                }*/
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
        recyclerView.setAdapter(new ExerciseAdapter(data));
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
        getMenuInflater().inflate(R.menu.menu_config, menu);
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

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_calendar) {
            startActivity(new Intent(TrainingScheduleActivity.this, CalendarActivity.class));
            finish();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.rodrigoftw.myworkouttracker.myworkouttracker.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.rodrigoftw.myworkouttracker.myworkouttracker.R;
import com.rodrigoftw.myworkouttracker.myworkouttracker.fragment.MeasurementChartFragment;
import com.rodrigoftw.myworkouttracker.myworkouttracker.fragment.WeightChartFragment;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, HistoryActivity.class));
    }

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        setTitle(R.string.history_title);

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

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new WeightChartFragment(), "Peso");
        adapter.addFragment(new MeasurementChartFragment(), "Medidas");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
            startActivity(new Intent(ctx, TrainingScheduleActivity.class));
            /*finish();*/
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_calendar) {
            startActivity(new Intent(ctx, CalendarActivity.class));
            /*finish();*/
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

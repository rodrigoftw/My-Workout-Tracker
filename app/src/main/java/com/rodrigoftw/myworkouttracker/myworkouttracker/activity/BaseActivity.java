package com.rodrigoftw.myworkouttracker.myworkouttracker.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.rodrigoftw.myworkouttracker.myworkouttracker.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/*import com.j256.ormlite.android.apptools.OpenHelperManager;*/
/*import com.remediozap.adapter.NavMenuAdapter;*/

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected Context ctx;
    protected Toolbar toolbar;
    protected TextView toolbarTitle;
    protected FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void setupDrawerLayout(){

    }

    protected void setupToolbar(){
        // toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setElevation(1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Log.e("BaseActivity", String.valueOf(toolbarTitle != null));

        if (toolbarTitle != null) {
            try {
                ActivityInfo activityInfo = getPackageManager().getActivityInfo(
                        getComponentName(), PackageManager.GET_META_DATA);
                String title = activityInfo.loadLabel(getPackageManager())
                        .toString();
                toolbarTitle.setText(title);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_config, menu);
        return true;
    }

    /*@Override
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
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_schedule) {
            startActivity(new Intent(BaseActivity.this, TrainingScheduleActivity.class));
            /*finish();*/
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(BaseActivity.this, HistoryActivity.class));
            /*finish();*/
        } else if (id == R.id.nav_calendar) {
            startActivity(new Intent(BaseActivity.this, CalendarActivity.class));
            /*finish();*/
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(BaseActivity.this, UserDataActivity.class));
            /*finish();*/
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(BaseActivity.this, HelpActivity.class));
            /*finish();*/
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(BaseActivity.this, AboutActivity.class));
        } else if (id == R.id.nav_logout) {
            logOutDialog(ctx);
            /*startActivity(new Intent(BaseActivity.this, LoginActivity.class));
            finish();*/
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected String formatDate(@NonNull String dateTemplate, @NonNull Date date) {
        return new SimpleDateFormat(dateTemplate, Locale.getDefault()).format(date);
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
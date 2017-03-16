package com.rodrigoftw.myworkouttracker.myworkouttracker.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.rodrigoftw.myworkouttracker.myworkouttracker.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/*import com.j256.ormlite.android.apptools.OpenHelperManager;*/
/*import com.remediozap.adapter.NavMenuAdapter;*/

public class BaseActivity extends AppCompatActivity {

    protected Context ctx;
    protected Toolbar toolbar;
    protected TextView toolbarTitle;

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
}
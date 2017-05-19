package com.rodrigoftw.myworkouttracker.myworkouttracker.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.rodrigoftw.myworkouttracker.myworkouttracker.R;

/**
 * Created by Rodrigo on 12/05/2017.
 */

public class AboutActivity extends BaseActivity {

    /**
     * Class attributes
     */

    private Context ctx;
    private TextView aboutTextView;
    private TextView aboutTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        setTitle(R.string.about_title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupToolbar();

        // save context
        this.ctx = this;

        aboutTextView = (TextView) findViewById(R.id.abouTextView);
        aboutTextView2 = (TextView) findViewById(R.id.abouTextView2);

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
    public void onBackPressed() {}

}

package com.rodrigoftw.myworkouttracker.myworkouttracker.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.rodrigoftw.myworkouttracker.myworkouttracker.R;
import com.rodrigoftw.myworkouttracker.myworkouttracker.activity.BaseActivity;

/**
 * Created by Rodrigo on 16/04/2017.
 */

public class BaseFragment extends Fragment {

    protected BaseActivity ctx;
    protected Toolbar toolbar;
    protected TextView toolbarTitle;
    protected View layout;
    protected String fragmentTitle;

    protected void setupToolbar() {
        // toolbar
        toolbar = (Toolbar) layout.findViewById(R.id.toolbar);
        toolbarTitle = (TextView) layout.findViewById(R.id.toolbarTitle);
        ctx.setSupportActionBar(toolbar);
        // getSupportActionBar().setElevation(1);

        ctx.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ctx.getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (toolbarTitle != null) {
            toolbarTitle.setText(fragmentTitle);
        }
    }
}

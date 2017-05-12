package com.rodrigoftw.myworkouttracker.myworkouttracker.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.rodrigoftw.myworkouttracker.myworkouttracker.R;
import com.rodrigoftw.myworkouttracker.myworkouttracker.adapter.HelpAdapter;
import java.util.ArrayList;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Rodrigo on 23/02/2017.
 */

public class HelpActivity extends BaseActivity {

    /*private ViewPager viewPager;*/

    /**
     * Class attributes
     */

    private Context ctx;
    private ViewPager helpViewPager;
    private int currentOfferPage = 0;
    private Button btnQuitHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_help);

        // save context
        this.ctx = this;

        this.helpViewPager = (ViewPager) findViewById(R.id.helpViewPager);

        ArrayList<String> images = new ArrayList<>();
        images.add("https://s-media-cache-ak0.pinimg.com/originals/cc/90/b0/cc90b02bd38ee748ca1f1b4012cddca9.jpg");
        images.add("http://sherly.mobile9.com/download/media/656/photo04-01_46O28TyU.jpg");
        images.add("http://sherly.mobile9.com/download/media/656/simple-lif_fMFlFm1B.jpg");
        images.add("https://s-media-cache-ak0.pinimg.com/236x/9d/cb/4a/9dcb4affc5f8bed7a877dfdf6583799c.jpg");
        images.add("https://s-media-cache-ak0.pinimg.com/564x/59/b6/61/59b6613dfc22a82634de0f6a96ee7152.jpg");

        HelpAdapter helpAdapter = new HelpAdapter(ctx, images);
        CircleIndicator indicatorHelp = (CircleIndicator) findViewById(R.id.indicatorHelp);
        helpViewPager.setAdapter(helpAdapter);
        indicatorHelp.setViewPager(helpViewPager);
        helpViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentOfferPage = position;
            }

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {}

        });

        btnQuitHelp = (Button) findViewById(R.id.btnQuitTutorial);
        btnQuitHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {}

}

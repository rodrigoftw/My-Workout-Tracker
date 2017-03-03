package com.rodrigoftw.myworkouttracker.myworkouttracker;

import android.app.Application;
import android.content.Context;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public final class MyWorkoutTrackerApplication extends Application {
    /**
     * Fonts statics
     */
    private static final String DEFAULT_REGULAR = "montserrat/regular.ttf";
    private static final String DEFAULT_BOLD = "montserrat/bold.ttf";

    /**
     * Instances
     */
    private static Context context;
    /*public static User user;*/

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Quicksand-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        MyWorkoutTrackerApplication.context = getApplicationContext();

        // use iconify
        Iconify.with(new FontAwesomeModule());

        // overrride font
        //TypeFaceUtil.overrideFont(getApplicationContext(), "NORMAL", "fonts/Quicksand-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        /*FontsOverride.setDefaultFont(this, "BOLD", "fonts/Quicksand-Bold.ttf");
        FontsOverride.setDefaultFont(this, "LIGHT", "fonts/Quicksand-Light.ttf");
        FontsOverride.setDefaultFont(this, "MEDIUM", "fonts/Quicksand-Medium.ttf");
        FontsOverride.setDefaultFont(this, "REGULAR", "fonts/Quicksand-Regular.ttf");*/
        /*FontsOverride.setDefaultFont(this, "BOLD", "Quicksand_Bold.otf");
        FontsOverride.setDefaultFont(this, "BOLD_OBLIQUE", "Quicksand_Bold_Oblique.otf");
        FontsOverride.setDefaultFont(this, "BOOK", "Quicksand_Book.otf");
        FontsOverride.setDefaultFont(this, "BOOK_OBLIQUE", "Quicksand_Book_Oblique.otf");
        FontsOverride.setDefaultFont(this, "DASH", "Quicksand_Dash.otf");
        FontsOverride.setDefaultFont(this, "LIGHT", "Quicksand_Light.otf");
        FontsOverride.setDefaultFont(this, "LIGHT_OBLIQUE", "Quicksand_Light_Oblique.otf");*/
        /*FontsOverride.setDefaultFont(this, "MONOSPACE", DEFAULT_REGULAR);*/

        // log
        // Log.e("oncreateppliaction", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

        // set base app

    }

    public static Context getInstance() {
        return MyWorkoutTrackerApplication.context;
    }

    /*public static AsyncHttpClient http() {
        return new AsyncHttpClient();
    }*/
}

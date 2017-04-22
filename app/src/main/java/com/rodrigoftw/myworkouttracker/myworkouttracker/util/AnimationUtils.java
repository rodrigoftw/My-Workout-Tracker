package com.rodrigoftw.myworkouttracker.myworkouttracker.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/*import static io.blackbox_vision.materialcalendarview.internal.utils.ScreenUtils.getScreenHeight;*/

/**
 * Created by Rodrigo on 30/03/2017.
 */

public final class AnimationUtils {

    private AnimationUtils() { }

    public static void animate(@NonNull View v, @NonNull Context ctx) {
        /*ViewCompat.setTranslationY(v, getScreenHeight(ctx));*/
        ViewCompat.setAlpha(v, 0f);
        ViewCompat.animate(v)
                .translationY(0f)
                .setDuration(700)
                .alpha(1f)
                .setInterpolator(new DecelerateInterpolator(3.0f))
                .start();
    }
}

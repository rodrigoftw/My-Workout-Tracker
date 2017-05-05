package com.rodrigoftw.myworkouttracker.myworkouttracker.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.rodrigoftw.myworkouttracker.myworkouttracker.MyWorkoutTrackerApplication;
import com.rodrigoftw.myworkouttracker.myworkouttracker.R;
import com.rodrigoftw.myworkouttracker.myworkouttracker.controller.SessionController;
import com.rodrigoftw.myworkouttracker.myworkouttracker.http.Routes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Rodrigo on 01/05/2017.
 */
public class Util {
    public static final String FONT_ICON = "fontawesome-webfont.ttf";
    public static void setFont(Context ctx, String fontPath, TextView... textViews) {
        Typeface tf = Typeface.createFromAsset(ctx.getAssets(), fontPath);

        for (TextView textView : textViews) {
            textView.setTypeface(tf);
        }
    }

    public static Bitmap resizeBitmap(Context ctx, int resId, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(ctx.getResources(), resId);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    public static boolean checkError(JSONObject jsonObject) {
        try {
            return jsonObject.getBoolean("error");
        } catch (Exception e) {
            return false;
        }
    }

    public static String getError(JSONObject jsonObject) {
        try {
            if (jsonObject.has("error")) {
                return jsonObject.getString("error");
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    public static ArrayList<String> getErrors(JSONObject jsonObject) {
        try {
            if (jsonObject.getJSONArray("errors") != null) {
                ArrayList<String> listdata = new ArrayList<String>();
                JSONArray jArray = jsonObject.getJSONArray("errors");
                if (jArray != null) {
                    for (int i=0;i<jArray.length();i++){
                        listdata.add(jArray.get(i).toString());
                    }
                }

                return listdata;
            }
        } catch (Exception e){
            return null;
        }

        return null;
    }

    public static Dialog loadingDialog(Context ctx) {
        Dialog loading = new Dialog(ctx);
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loading.setContentView(R.layout.dialog_loading);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loading.setCanceledOnTouchOutside(false);
        loading.setCancelable(false);

        return loading;
    }

    public static void alert(Context ctx, String title, String message) {
        new AlertDialog.Builder(ctx)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    public static String getDeviceID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "";// + tm.getSimSerialNumber();
        androidId = ""
                + android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();

        return deviceId;
    }

    public static String getTokenAccess() {
        Context ctx = MyWorkoutTrackerApplication.getInstance();
        SharedPreferences mPreferences = ctx.getSharedPreferences(SessionController.SESSION_PREF_NAME, ctx.MODE_PRIVATE);

        return mPreferences.getString("token", null);
    }

    public static File getOutputMediaFile(String fileName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                /*+ BuildConfig.APPLICATION_ID*/
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return mediaFile;
    }

    public static Bitmap getRoundedCornerBitmap(Context context, Bitmap input, int pixels , int w , int h , boolean squareTL, boolean squareTR, boolean squareBL, boolean squareBR  ) {

        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);

        //make sure that our rounded corner is scaled appropriately
        final float roundPx = pixels*densityMultiplier;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);


        //draw rectangles over the corners we want to be square
        if (squareTL ){
            canvas.drawRect(0, h/2, w/2, h, paint);
        }
        if (squareTR ){
            canvas.drawRect(w/2, h/2, w, h, paint);
        }
        if (squareBL ){
            canvas.drawRect(0, 0, w/2, h/2, paint);
        }
        if (squareBR ){
            canvas.drawRect(w/2, 0, w, h/2, paint);
        }


        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(input, 0,0, paint);

        return output;
    }

    public static void setStatusBarColor(Activity activity, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                MyWorkoutTrackerApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void putPref(String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyWorkoutTrackerApplication.getInstance());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getPref(String key, String defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyWorkoutTrackerApplication.getInstance());
        return preferences.getString(key, defValue);
    }

    public static String getBaseApp() {
        return Util.getPref("BASE_APP", Routes.DEFAULT_BASE_APP);
    }

    public static String limitString(String value, int limit) {
        if (value.length() <= limit) {
            return value;
        }

        return value.substring(0, limit-1) + "...";
    }
}
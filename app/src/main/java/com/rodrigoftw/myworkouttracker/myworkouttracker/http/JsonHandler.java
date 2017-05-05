package com.rodrigoftw.myworkouttracker.myworkouttracker.http;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.rodrigoftw.myworkouttracker.myworkouttracker.activity.LoginActivity;
import com.rodrigoftw.myworkouttracker.myworkouttracker.controller.SessionController;
import com.rodrigoftw.myworkouttracker.myworkouttracker.util.Util;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Rodrigo on 01/05/2017.
 */
public class JsonHandler extends JsonHttpResponseHandler {
    /**
     * Class attributes
     */
    private Context mContext;

    public JsonHandler(Context ctx) {
        // save context
        this.mContext = ctx;
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        if (statusCode == 401) {
            checkTokenError(errorResponse);
        } else {
            Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
        for( String line : response.split("\n") ) {
            Log.d( "error", line );
        }
        throwable.printStackTrace();
        Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Verificar a existência de erro na resposta de uma requisição errada.
     * @param errorResponse
     */
    private void checkTokenError(JSONObject errorResponse) {
        String error = Util.getError(errorResponse);
        if (error != null) {
            // remove token
            SessionController sessionController = new SessionController(mContext);
            sessionController.removeToken();

            // create toast to message
            Toast.makeText(mContext, "Sua sessão expirou!\nTente novamente!", Toast.LENGTH_SHORT).show();

            // close all activities and start the login activity
            Intent intent = new Intent(mContext, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mContext.startActivity(intent);
        }
    }

    /**
     * AlertDialog para erros
     * @param title
     * @param message
     */
    public void alert(String title, String message) {
        Util.alert(mContext, title, message);
    }
}

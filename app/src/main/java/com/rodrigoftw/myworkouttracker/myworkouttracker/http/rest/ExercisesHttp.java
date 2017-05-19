package com.rodrigoftw.myworkouttracker.myworkouttracker.http.rest;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.rodrigoftw.myworkouttracker.myworkouttracker.http.JsonHandler;
import com.rodrigoftw.myworkouttracker.myworkouttracker.http.Routes;
import com.rodrigoftw.myworkouttracker.myworkouttracker.model.Exercise;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Rodrigo on 19/05/2017.
 */
public class ExercisesHttp extends AuthenticatedHttp {
    /**
     * Class attributes
     */
    private AsyncHttpClient client;
    private Context ctx;

    public ExercisesHttp(Context ctx) {
        this.ctx = ctx;

        // start aync http client
        client = new AsyncHttpClient();
    }

    public void getExercises(String query, final ExercisesHttp.CallbackIndex callback) {
        // location params
        RequestParams params = new RequestParams();

        if (query != null) {
            params.put("search_query", query);
        }

        Log.e("teste", Routes.getApiRoute(Routes.LIST_EXERCISES));

        client = getAuthHeader(client);
        client.get(ctx, Routes.getApiRoute(Routes.LIST_EXERCISES), params, new JsonHandler(ctx) {
            @Override
            public void onFinish() {
                callback.finish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // retrieving
                ArrayList<Exercise> exercisesList = new ArrayList<Exercise>();
                JSONObject loopObject;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        loopObject = response.getJSONObject(i);

                        // create new object
                        int id = Integer.parseInt(loopObject.getString("id"));
                        String name = loopObject.getString("name");
                        int sets = loopObject.getInt("sets");
                        int reps = loopObject.getInt("reps");
                        int rest = loopObject.getInt("rest");

                        Exercise loopExercise = new Exercise(id, name, sets, reps, rest);

                        // add to list
                        exercisesList.add(loopExercise);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("Error", e.getMessage());
                    }
                }

                // callback
                callback.index(exercisesList);
            }
        });
    }

    public void getExercise(int id, JsonHandler jsonHandler) {
        client = getAuthHeader(client);
        client.get(Routes.getApiRoute(Routes.EXERCISE_SHOW).replace("{id}", ""+id), jsonHandler);
    }

    /**
     * Callback functions
     */
    public interface CallbackIndex {
        void index(ArrayList<Exercise> exercisesList);
        void finish();
    }
}

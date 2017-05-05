package com.rodrigoftw.myworkouttracker.myworkouttracker.http.rest;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by Rodrigo on 01/05/2017.
 */

public class AuthenticatedHttp {
    /**
     * Class attributes
     */
    protected AsyncHttpClient client;
    protected Context ctx;
    private static final int CONNECTION_TIMEOUT = 20*1000;

    protected AsyncHttpClient getAuthHeader(AsyncHttpClient client) {
        return client;
    }

    protected void setupClient() {
        // start aync http client
        client = new AsyncHttpClient();
        client.setTimeout(AuthenticatedHttp.CONNECTION_TIMEOUT);
    }
}

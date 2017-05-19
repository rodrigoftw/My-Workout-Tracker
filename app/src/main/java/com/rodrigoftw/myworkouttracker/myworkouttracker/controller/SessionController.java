package com.rodrigoftw.myworkouttracker.myworkouttracker.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.rodrigoftw.myworkouttracker.myworkouttracker.MyWorkoutTrackerApplication;
import com.rodrigoftw.myworkouttracker.myworkouttracker.activity.LoginActivity;
import com.rodrigoftw.myworkouttracker.myworkouttracker.activity.TrainingScheduleActivity;
import com.rodrigoftw.myworkouttracker.myworkouttracker.http.JsonHandler;
import com.rodrigoftw.myworkouttracker.myworkouttracker.http.Routes;
import com.rodrigoftw.myworkouttracker.myworkouttracker.util.Util;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Rodrigo on 01/05/2017.
 */
public class SessionController {
    // Identificação da sessão
    public static final String SESSION_PREF_NAME = "AppSessionPreferences";

    // Context
    private Context ctx;

    /**
     * Identificação dos tipos de autenticação do login por redes sociais
     */
    //public static final int AUTH_FACEBOOK = 1;
    public static final int AUTH_GOOGLE = 2;


    public SessionController(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * Tentar fazer o login com email e senha
     * @param email
     * @param password
     */
    public void attempt(String email, String password) {
        // Loading dialog
        final Dialog loadingDialog = Util.loadingDialog(ctx);
        loadingDialog.show();

        // Parâmetros da requisição
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);

        // Enviando requisição
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Routes.getApiRoute(Routes.LOGIN), params, new JsonHandler(ctx) {
            @Override
            public void onFinish() {
                loadingDialog.cancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // Salvar o token em SharedPreferences
                    saveToken(response.getString("token"));

                    // Ir para a activity principal após logado
                    ctx.startActivity(new Intent(ctx, TrainingScheduleActivity.class));
                    ((LoginActivity) ctx).finish();
                } catch (Exception e) {
                    alert("Falha no login", "Não foi possível pegar o token de acesso, tente novamente!");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                String message = Util.getError(errorResponse);
                alert("Falha no login", message);
            }
        });
    }

    /**
     * Tentar fazer o login por facebook/google
     * @param token
     */
    /*public void attempt(String token, final int type) {
        // Loading dialog
        final Dialog loadingDialog = Util.loadingDialog(ctx);
        loadingDialog.show();

        // Parâmetros da requisição
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("type", type);

        // Enviando requisição
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(Routes.getApiRoute(Routes.SOCIAL_LOGIN), params, new JsonHandler(ctx) {
            @Override
            public void onFinish() {
                loadingDialog.cancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // Verificar se o usuário não está cadastrado
                   *//* if (response.has("user_guest")) {
                        new AlertDialog.Builder(ctx)
                                .setTitle("Bem vindo!")
                                .setMessage("Olá, acabamos de verificar que você ainda não está cadastrado em nosso sistema, deseja registrar-se?")
                                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(ctx, RegisterActivity.class);
                                        intent.putExtra("type", type);
                                        ctx.startActivity(intent);
                                    }
                                })
                                .setNegativeButton("Agora não", null)
                                .show();
                        return;
                    }*//*

                    Log.e("SESSIONCONTROLLER", response.toString());

                    // Salvar o token em SharedPreferences
                    saveToken(response.getString("token"));

                    // Ir para a activity principal após logado
                    ctx.startActivity(new Intent(ctx, TrainingScheduleActivity.class));
                    ((LoginActivity) ctx).finish();
                } catch (Exception e) {
                    //Não foi possível pegar o token de acesso
                    alert("Falha no login", "Algo de errado ocorreu, tente novamente!");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                String message = Util.getError(errorResponse);
                alert("Falha no login, tente novamente", message);
            }
        });
    }*/

    /**
     * Salvar o token de acesso a API
     * @param token
     */
    private void saveToken(String token) {
        SharedPreferences.Editor editor = getSessionSharedPreferences();
        editor.putString("token", token);
        editor.apply();
    }

    /**
     * Checar se usuário está logado
     */
    public static boolean verifyLogin() {
        Context ctx = MyWorkoutTrackerApplication.getInstance();
        SharedPreferences mPreferences = ctx.getSharedPreferences(SessionController.SESSION_PREF_NAME, ctx.MODE_PRIVATE);

        return mPreferences.contains("token");
    }

    /**
     * Remover token de acesso a API
     */
    public void removeToken() {
        SharedPreferences.Editor editor = getSessionSharedPreferences();
        editor.remove("token");
        editor.apply();
    }

    /**
     * Pegar shared preferences relativos as sessões
     */
    private SharedPreferences.Editor getSessionSharedPreferences() {
        SharedPreferences mPreferences = ctx.getSharedPreferences(SessionController.SESSION_PREF_NAME, ctx.MODE_PRIVATE);
        return mPreferences.edit();
    }
}

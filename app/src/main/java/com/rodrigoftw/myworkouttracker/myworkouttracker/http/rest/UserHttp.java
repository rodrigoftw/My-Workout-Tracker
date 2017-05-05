package com.rodrigoftw.myworkouttracker.myworkouttracker.http.rest;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.rodrigoftw.myworkouttracker.myworkouttracker.R;
import com.rodrigoftw.myworkouttracker.myworkouttracker.http.JsonHandler;
import com.rodrigoftw.myworkouttracker.myworkouttracker.http.Routes;
import com.rodrigoftw.myworkouttracker.myworkouttracker.model.User;
import com.rodrigoftw.myworkouttracker.myworkouttracker.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Rodrigo on 01/05/2017.
 */
public class UserHttp extends AuthenticatedHttp {

    public UserHttp(Context ctx) {
        this.ctx = ctx;

        // setup http
        setupClient();
    }

    /*public void register(String name, final String email, String phone,
                         final String password, String uid, String register_type, String token, final Dialog loadingDialog) {
        // request params
        RequestParams params = new RequestParams();
        params.add("name", name);
        params.add("email", email);
        params.add("phone", phone);

        if (password != null) {
            params.add("password", password);
        }

        params.add("uid", uid);
        params.add("register_type", register_type);
        params.add("token", token);

        client.post(ctx, Routes.getApiRoute(Routes.REGISTER), params, new JsonHandler(ctx) {
            @Override
            public void onFinish() {
                loadingDialog.cancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Intent intent = new Intent(ctx, CompleteRegisterActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    ctx.startActivity(intent);
                } catch (Exception e) {
                    alert("Falha no registro", "Não foi possível realizar o registro, tente novamente!");
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ArrayList<String> errorsList = Util.getErrors(errorResponse);
                if (errorsList != null) {
                    String errorsString = TextUtils.join("\n", errorsList);
                    alert("Erro!", errorsString);
                }
            }
        });
    }*/

    public void profile(final UserHttp.ProfileCallback callback) {
        client = getAuthHeader(client);
        client.get(Routes.getApiRoute(Routes.USER_ME), new JsonHandler(ctx) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.e("/me", response.toString());
                    int id = response.getInt("id");
                    String name = response.getString("name");
                    String uid = response.getString("uid");
                    String email = response.getString("email");
                    int type = response.getInt("type");
                    String phone = response.getString("phone");

                    String image = null;
                    /*if (type == ApplicationOptions.EMAIL_LOGIN_TYPE) {*/
                        if (!response.isNull("image")) {
                            image = response.getString("image");
                        }
                    /*}*//* else if (type == ApplicationOptions.FACEBOOK_LOGIN_TYPE) {
                        image = "http://graph.facebook.com/" + uid + "/picture?width=500&height=500";
                    }*/

                    User user = new User(id, name, email, type, uid, image, phone);
                    callback.success(user);
                } catch (Exception e) {
                }
            }
        });
    }

    public void update(RequestParams params, final UserHttp.UpdateCallback callback) {
        client = getAuthHeader(client);
        client.post(Routes.getApiRoute(Routes.USER_UPDATE), params, new JsonHandler(ctx) {
            @Override
            public void onFinish() {
                super.onFinish();
                callback.finish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("asdasd", response.toString());
                callback.success(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == 400) {
                    callback.fail(errorResponse);
                    return;
                }

                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void forgotPassword(String email, final Dialog loadingDialog) {
        // request params
        RequestParams params = new RequestParams();
        params.add("email", email);

        client.setTimeout(30*1000);
        client.post(ctx, Routes.getApiRoute(Routes.FORGOT_PASSWORD), params, new JsonHandler(ctx) {
            @Override
            public void onFinish() {
                loadingDialog.cancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if(response.getBoolean("success")){
                        alert("Sucesso!", ctx.getString(R.string.forgot_password_success));
                    }
                } catch (Exception e) {
                    alert("Falha", "Não foi possível enviar o email, tente novamente!");
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ArrayList<String> errorsList = Util.getErrors(errorResponse);
                if (errorsList != null) {
                    String errorsString = TextUtils.join("\n", errorsList);
                    alert("Erro!", errorsString);
                }
            }
        });
    }

    public interface ProfileCallback {
        void success(User user);
    }

    public interface UpdateCallback {
        void success(JSONObject response);
        void fail(JSONObject response);
        void finish();
    }
}

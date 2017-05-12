package com.rodrigoftw.myworkouttracker.myworkouttracker.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.rodrigoftw.myworkouttracker.myworkouttracker.MyWorkoutTrackerApplication;
import com.rodrigoftw.myworkouttracker.myworkouttracker.R;
import com.rodrigoftw.myworkouttracker.myworkouttracker.exception.InvalidFormException;
import com.rodrigoftw.myworkouttracker.myworkouttracker.helpers.Validator;
import com.rodrigoftw.myworkouttracker.myworkouttracker.http.Routes;
import com.rodrigoftw.myworkouttracker.myworkouttracker.http.rest.UserHttp;
import com.rodrigoftw.myworkouttracker.myworkouttracker.model.User;
import com.rodrigoftw.myworkouttracker.myworkouttracker.util.ImagePickerUtil;
import com.rodrigoftw.myworkouttracker.myworkouttracker.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Rodrigo on 10/05/2017.
 */

public class UserDataActivity extends BaseActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etNewPassword;
    private EditText etConfirmNewPassword;
    private ImageView userImage;
    private Button saveUserData;

    /*private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference userReference = databaseReference.child("users");*/

    /**
     * Class attributes
     */

    private Context ctx;
    private User user;
    /*private boolean facebookUser;*/
    private Bitmap selectedImage;
    private UserHttp http;
    private ProgressDialog progressDialog;
    private File tempFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        setTitle(R.string.user_data_title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        setupToolbar();

        // save context
        this.ctx = this;
        /*http = new UserHttp(ctx);*/
        progressDialog = new ProgressDialog(this);

        // user data layout components
        userImage = (ImageView) findViewById(R.id.userImage);
        etName = (EditText) findViewById(R.id.userName);
        etEmail = (EditText) findViewById(R.id.userEmail);
        etPassword = (EditText) findViewById(R.id.userPassword);
        etNewPassword = (EditText) findViewById(R.id.userNewPassword);
        etConfirmNewPassword = (EditText) findViewById(R.id.userConfirmNewPassword);
        saveUserData = (Button) findViewById(R.id.btnSaveUserData);

        // start progress dialog
        progressDialog = new ProgressDialog(this, R.style.AlertDialogCustom);
        progressDialog.setCancelable(false);

        // save current user
        user = MyWorkoutTrackerApplication.user;

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseImageIntent = ImagePickerUtil.getPickImageIntent(ctx);
                startActivityForResult(chooseImageIntent, 1);
            }
        });

        saveUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        /*setData();*/

        /*etConfirmNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // send email to recover account
                    validate();

                } catch (InvalidFormException e) {
                    e.getElem().requestFocus();
                    e.getElem().setError(e.getMessage());
                }
            }
        });*/
    }

    /*private void setData() {
        etName.setText(user.getName());
        etEmail.setText(user.getEmail());

        if (user.getImage() != null) {
            Glide.clear(userImage);
            Glide.with(ctx)
                .load(user.getImage())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(userImage);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            // get and show image
            selectedImage = ImagePickerUtil.getImageFromResult(this, resultCode, data);
            userImage.setImageBitmap(selectedImage);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void save() {
        try {
            // validate form
            validate();

            // Fazer a requisição para a API, para autenticar os dados fornecidos pelo usuário
            submit();
        } catch (InvalidFormException e) {
            e.getElem().requestFocus();
            e.getElem().setError(e.getMessage());
        }
    }

    private void submit() {
        // request params
        final RequestParams params = new RequestParams();

        params.put("name", etName.getText().toString());
        params.put("current_password", etPassword.getText().toString());
        params.put("new_password", etNewPassword.getText().toString());
        params.put("new_password_confirmation", etConfirmNewPassword.getText().toString());

        // Add image if selected
        if (selectedImage != null) {
            new AsyncTask<Void, Void, String>() {
                protected void onPreExecute() {
                    progressDialog.show();
                    progressDialog.setMessage("Salvando imagem...");
                };

                @Override
                protected String doInBackground(Void... fParams) {
                    try {
                        File outputDir = ctx.getCacheDir();
                        tempFile = File.createTempFile("image", "png", outputDir);
                        FileOutputStream filecon = new FileOutputStream(tempFile);
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 90, filecon);

                        // add param
                        params.put("image", "image.png", tempFile);

                        // close
                        if (filecon != null) {
                            filecon.flush();
                            filecon.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return "";
                }

                @Override
                protected void onPostExecute(String msg) {
                    update(params);
                }
            }.execute(null, null, null);
        } else {
            update(params);
        }
    }

    private void update(RequestParams params) {
        progressDialog.show();
        progressDialog.setMessage("Atualizando dados...");

        http.update(params, new UserHttp.UpdateCallback() {
            @Override
            public void success(JSONObject response) {
                try {
                    boolean success = response.getBoolean("success");
                    if (success) {
                        user.setName(etName.getText().toString());
                        user.setPhone(etEmail.getText().toString());
                        user.setImage(Routes.getRoute(Routes.UPLOAD) + "/users/" + user.getId() + "/profile/full.png");
                        MyWorkoutTrackerApplication.user = user;
                        Toast.makeText(ctx, "Dados atualizados com sucesso!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {}
            }

            @Override
            public void fail(JSONObject response) {
                try {
                    JSONArray errors = response.getJSONArray("errors");
                    String allErrors = "";
                    for (int i = 0; i < errors.length(); i++) allErrors += errors.getString(i) + "\n";
                    Util.alert(ctx, "Erro ao salvar dados!", allErrors);
                } catch (Exception e) { e.printStackTrace(); }
            }

            @Override
            public void finish() {
                progressDialog.dismiss();

                if (tempFile != null && tempFile.exists()) {
                    tempFile.delete();
                }
            }
        });
    }

    /**
     * Validar os dados inseridos pelo usuário no campo de e-mail
     */

    private void validate() throws InvalidFormException {
        String emailValue = etEmail.getText().toString().trim();
        String passwordValue = etPassword.getText().toString().trim();
        String newPasswordValue = etNewPassword.getText().toString().trim();
        String confirmNewPasswordValue = etConfirmNewPassword.getText().toString().trim();

        if (!Validator.email(emailValue)) {
            throw new InvalidFormException("Por favor, forneça um e-mail válido!", etEmail);
        }

        if(emailValue.isEmpty()){
            throw new InvalidFormException("Por favor, insira um e-mail!", etEmail);
        }

        if(passwordValue.isEmpty()){
            throw new InvalidFormException("Por favor, insira sua senha!", etPassword);
        }

        if(newPasswordValue.isEmpty()){
            throw new InvalidFormException("Por favor, insira sua senha!", etNewPassword);
        }

        if(confirmNewPasswordValue.isEmpty()){
            throw new InvalidFormException("Por favor, insira sua senha!", etConfirmNewPassword);
        }

        /*User user = new User();
        userReference.child("001").setValue(user);*/

        progressDialog.setMessage("Atualizando seus dados...");
        progressDialog.show();
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    /*private void emailValidationSuccessDialog(final Context ctx) {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ctx, R.style.AlertDialogCustom));
        builder.setTitle("Sucesso!")
                .setMessage("Enviamos instruções ao seu e-mail. Entre no link enviado para mudar sua senha.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        finish();
                        startActivity(new Intent(ctx, LoginActivity.class));
                    }
                });
                *//*.setNegativeButton(R.string.reject_order_cancelled, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });*//*
        final AlertDialog alert = builder.create();
        alert.show();

    }

    private void emailValidationFailureDialog(final Context ctx) {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ctx, R.style.AlertDialogCustom));
        builder.setTitle("E-mail inválido!")
                .setMessage("O e-mail inserido não foi encontrado em nosso sistema, utilize seu e-mail cadastrado ou fale com o responsável por seu cadastro na academia.")
                .setPositiveButton("Tentar Novamente", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        finish();
                        startActivity(new Intent(ctx, UserDataActivity.class));
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        finish();
                        startActivity(new Intent(ctx, LoginActivity.class));
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }*/

    @Override
    public void onBackPressed() { finish(); }
}

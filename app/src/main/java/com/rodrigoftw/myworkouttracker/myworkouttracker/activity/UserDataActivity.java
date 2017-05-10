package com.rodrigoftw.myworkouttracker.myworkouttracker.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rodrigoftw.myworkouttracker.myworkouttracker.R;
import com.rodrigoftw.myworkouttracker.myworkouttracker.exception.InvalidFormException;
import com.rodrigoftw.myworkouttracker.myworkouttracker.helpers.Validator;
import com.rodrigoftw.myworkouttracker.myworkouttracker.model.User;

/**
 * Created by Rodrigo on 10/05/2017.
 */

public class UserDataActivity extends BaseActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etNewPassword;
    private EditText etConfirmNewPassword;
    private ProgressDialog progressDialog;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference userReference = databaseReference.child("users");

    /**
     * Class attributes
     */

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        setTitle(R.string.user_data_title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupToolbar();

        // save context
        this.ctx = this;
        progressDialog = new ProgressDialog(this);

        // recover password layout components
        etName = (EditText) findViewById(R.id.userName);
        etEmail = (EditText) findViewById(R.id.userEmail);
        etPassword = (EditText) findViewById(R.id.userPassword);
        etNewPassword = (EditText) findViewById(R.id.userNewPassword);
        etConfirmNewPassword = (EditText) findViewById(R.id.userConfirmNewPassword);
        Button etConfirmNewPassword = (Button) findViewById(R.id.btnSaveUserData);

        etConfirmNewPassword.setOnClickListener(new View.OnClickListener() {
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
        });
    }

    /**
     * Validar os dados inseridos pelo usuário no campo de e-mail
     */

    private void validate() throws InvalidFormException {
        String emailValue = etEmail.getText().toString().trim();

        if (!Validator.email(emailValue)) {
            throw new InvalidFormException("Por favor, forneça um e-mail válido!", etEmail);
        }

        if(emailValue.isEmpty()){
            throw new InvalidFormException("Por favor, insira um e-mail!", etEmail);
        }

        User user = new User();
        userReference.child("001").setValue(user);

        progressDialog.setMessage("Atualizando seus dados...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        firebaseAuth.sendPasswordResetEmail(emailValue)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            emailValidationSuccessDialog(ctx);
                            /*Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();*/
                        } else {
                            emailValidationFailureDialog(ctx);
                            /*Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();*/
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void emailValidationSuccessDialog(final Context ctx) {

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
                /*.setNegativeButton(R.string.reject_order_cancelled, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });*/
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

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

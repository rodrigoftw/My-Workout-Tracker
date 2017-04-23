package com.rodrigoftw.myworkouttracker.myworkouttracker.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.rodrigoftw.myworkouttracker.myworkouttracker.R;
import com.rodrigoftw.myworkouttracker.myworkouttracker.exception.InvalidFormException;
import com.rodrigoftw.myworkouttracker.myworkouttracker.helpers.Validator;

/**
 * Created by Rodrigo on 23/02/2017.
 */

public class LoginActivity extends BaseActivity {

    private ImageView loginImage;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView forgotPassword;
    private ProgressDialog progressDialog;

    /**
     * Class attributes
     */

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        // save context
        this.ctx = this;
        progressDialog = new ProgressDialog(this);
        firebaseAuth = firebaseAuth.getInstance();

        // login layout components
        /*loginImage = (ImageView) findViewById(R.id.image_login);*/
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());
        btnLogin = (Button) findViewById(R.id.btnLogin);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // validate form
                    validate();

                    // Fazer a requisição para a API, para autenticar os dados fornecidos pelo usuário
                    //submit();
                } catch (InvalidFormException e) {
                    e.getElem().requestFocus();
                    e.getElem().setError(e.getMessage());
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

    }

    /**
     * Validar os dados inseridos pelo usuário no campo de e-mail
     */
    private void validate() throws InvalidFormException {
        String emailValue = etEmail.getText().toString().trim();
        String passwordValue = etPassword.getText().toString().trim();

        if (!Validator.email(emailValue)) {
            throw new InvalidFormException("Forneça um e-mail válido!", etEmail);
        }

        if (passwordValue.isEmpty()) {
            throw new InvalidFormException("Informe sua senha!", etPassword);
        }

        if (passwordValue.length() < 6 || passwordValue.length() > 10) {
            throw new InvalidFormException("Sua senha deve ter entre 6 e 10 caracteres!", etPassword);
        }

        progressDialog.setMessage("Fazendo login. Por favor, aguarde...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        firebaseAuth.signInWithEmailAndPassword(emailValue, passwordValue)
        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.dismiss();
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(), TrainingScheduleActivity.class));
                } else {
                    invalidCredentialsDialog(ctx);
                }
            }
        });
    }

    private void invalidCredentialsDialog(final Context ctx) {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ctx, R.style.AlertDialogCustom));
        builder.setTitle("Login inválido!")
                .setMessage("O e-mail ou a senha inseridos não foram encontrados, tente novamente.")
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

    @Override
    public void onBackPressed() { /*super.onBackPressed();*/ }

}

package com.rodrigoftw.myworkouttracker.myworkouttracker.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rodrigoftw.myworkouttracker.myworkouttracker.R;
import com.rodrigoftw.myworkouttracker.myworkouttracker.exception.InvalidFormException;
import com.rodrigoftw.myworkouttracker.myworkouttracker.helpers.Validator;

/**
 * Created by Rodrigo on 22/04/2017.
 */

public class ForgotPasswordActivity extends BaseActivity {

    private EditText etEmail;

    /**
     * Class attributes
     */

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        setTitle(R.string.forgot_password_title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupToolbar();

        // save context
        this.ctx = this;

        // recover password layout components
        etEmail = (EditText) findViewById(R.id.email);
        Button btnRecoverPassword = (Button) findViewById(R.id.btnRecoverPassword);

        btnRecoverPassword.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onBackPressed() {
        finish();
    }
}

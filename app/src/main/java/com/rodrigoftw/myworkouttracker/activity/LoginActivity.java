package com.rodrigoftw.myworkouttracker.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.rodrigoftw.myworkouttracker.R;

/**
 * Created by Rodrigo on 23/02/2017.
 */

public class LoginActivity extends BaseActivity{

    private ImageView loginImage;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;

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

        // register layout components
        loginImage = (ImageView) findViewById(R.id.image_login);
        etEmail = (EditText) findViewById(R.id.email);
        etPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);

    }

}

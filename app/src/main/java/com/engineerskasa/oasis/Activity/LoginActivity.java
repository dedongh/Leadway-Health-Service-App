package com.engineerskasa.oasis.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.engineerskasa.oasis.R;
import com.engineerskasa.oasis.Utility.Tools;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText txt_email, txt_password;
    private Button btn_sign_in;
    String name, phone, email, password, user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);

        initializeView();
    }

    private void initializeView() {
        txt_email = (TextInputEditText) findViewById(R.id.login_email);
        txt_password = (TextInputEditText) findViewById(R.id.login_password);
        btn_sign_in = (Button) findViewById(R.id.btn_sign_in);

        btn_sign_in.setOnClickListener(this);
    }

    public void showSignUpPage(View view) {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    @Override
    public void onClick(View v) {
        if (v == btn_sign_in) {
            sign_in_user();
        }
    }

    private void sign_in_user() {
    }
}
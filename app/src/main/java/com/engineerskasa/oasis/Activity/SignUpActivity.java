package com.engineerskasa.oasis.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.engineerskasa.oasis.R;
import com.engineerskasa.oasis.Utility.Tools;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private TextInputEditText txt_email, txt_password, txt_name, txt_phone;
    private Button btn_sign_up;

    String name, phone, email, password, user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);

        initializeView();
    }

    private void initializeView() {
        txt_email = (TextInputEditText) findViewById(R.id.sign_up_email);
        txt_name = (TextInputEditText) findViewById(R.id.sign_up_name);
        txt_phone = (TextInputEditText) findViewById(R.id.sign_up_phone);
        txt_password = (TextInputEditText) findViewById(R.id.sign_up_password);

        btn_sign_up = (Button) findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(this);


    }

    public void showSignInPage(View view) {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
    }

    @Override
    public void onClick(View v) {
        if (v == btn_sign_up) {
            sign_up_user();
        }
    }

    private void sign_up_user() {
    }
}
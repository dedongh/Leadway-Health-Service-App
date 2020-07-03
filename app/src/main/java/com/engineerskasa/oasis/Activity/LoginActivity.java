package com.engineerskasa.oasis.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.engineerskasa.oasis.Common.Common;
import com.engineerskasa.oasis.Database.DataSource.UserRepo;
import com.engineerskasa.oasis.Database.LocalDB.LeadWayDatabase;
import com.engineerskasa.oasis.Database.LocalDB.UserDataSource;
import com.engineerskasa.oasis.Model.User;
import com.engineerskasa.oasis.R;
import com.engineerskasa.oasis.Utility.Tools;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText txt_email, txt_password;
    private Button btn_sign_in;
    String name, phone, email, password, user_id;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);

        initDB();

        initializeView();
    }

    private void initDB() {
        Common.leadWayDatabase = LeadWayDatabase.getInstance(this);
        Common.userRepo = UserRepo.getInstance(UserDataSource.getInstance(Common.leadWayDatabase.userDAO()));
    }

    private void initializeView() {
        txt_email = (TextInputEditText) findViewById(R.id.login_email);
        txt_password = (TextInputEditText) findViewById(R.id.login_password);
        btn_sign_in = (Button) findViewById(R.id.btn_sign_in);

        btn_sign_in.setOnClickListener(this);

        SharedPreferences preferences = getSharedPreferences("USER_TOKEN", MODE_PRIVATE);
        //preferences.edit().remove("USER_TOKEN").apply();

        user_id = preferences.getString("token", null);

        if (user_id != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
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
        email = txt_email.getText().toString();
        password = txt_password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            txt_email.setError("Please enter email");
            txt_email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            txt_password.setError("Please enter password");
            txt_password.requestFocus();
            return;
        }
        if (password.length() < 6) {
            txt_password.setError("Password should be 6 or more characters");
            txt_password.requestFocus();
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPhone(phone);


        compositeDisposable.add(
                Common.userRepo.getSingleUserInfo(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    if (users != null) {
                        SharedPreferences preferences = getSharedPreferences("USER_TOKEN", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("token", String.valueOf(users.getId()));
                        editor.commit();
                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(this, ""+ throwable.getMessage(), Toast.LENGTH_SHORT).show();
                })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
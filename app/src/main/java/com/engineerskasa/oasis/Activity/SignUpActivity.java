package com.engineerskasa.oasis.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private TextInputEditText txt_email, txt_password, txt_name, txt_phone;
    private Button btn_sign_up;

    String name, phone, email, password, user_id;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);

        initDB();

        initializeView();

        getAllUsers();
    }

    private void getAllUsers() {
        compositeDisposable.add(
                Common.userRepo.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        Log.e("UIDS", "accept: "+ users.size() );
                        Log.e("UIDS", "user_info: "+ users );
                    }
                })
        );
    }

    private void initDB() {
        Common.leadWayDatabase = LeadWayDatabase.getInstance(this);
        Common.userRepo = UserRepo.getInstance(UserDataSource.getInstance(Common.leadWayDatabase.userDAO()));
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
        email = txt_email.getText().toString();
        password = txt_password.getText().toString();
        name = txt_name.getText().toString();
        phone = txt_phone.getText().toString();

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

        if (TextUtils.isEmpty(name)) {
            txt_name.setError("Please enter your name");
            txt_name.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            txt_phone.setError("Please enter phone number");
            txt_phone.requestFocus();
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setPhone(phone);
        user.setName(name);
        user.setPassword(password);

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                Common.userRepo.registerUser(user);
            }
        })
                .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(SignUpActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(SignUpActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
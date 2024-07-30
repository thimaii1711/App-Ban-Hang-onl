package com.example.appbanhangonl.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appbanhangonl.R;
import com.example.appbanhangonl.model.ToastHelper;
import com.example.appbanhangonl.retrofit.ApiBanHang;
import com.example.appbanhangonl.retrofit.RetrofitClient;
import com.example.appbanhangonl.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ResetPassActivity extends AppCompatActivity {

    EditText editTextResetPassEmail;
    AppCompatButton buttonResetPass;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        initView();
        initControll();
    }

    private void initControll() {
        buttonResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPassApp();
            }
        });
    }

    private void ResetPassApp() {
        String str_email = editTextResetPassEmail.getText().toString().trim();
        if (TextUtils.isEmpty(str_email)) {
            ToastHelper.showCustomToast(getApplicationContext(), "Vui lòng nhập Email !!!");
        } else {
            progressBar.setVisibility(View.VISIBLE);

            FirebaseAuth.getInstance().sendPasswordResetEmail(str_email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            ToastHelper.showCustomToast(getApplicationContext(), "Vui lòng kiểm tra Email của bạn !!!");
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            ToastHelper.showCustomToast(getApplicationContext(), "Email không chính xác !!!");
                        }
                    });

// Thay đổi pass ở host
//            compositeDisposable.add(apiBanHang.resetpassAPI(str_email)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                            user -> {
//                                if (user.isSucces()) {
//                                    ToastHelper.showCustomToast(getApplicationContext(), "Vui lòng kiểm tra email của bạn !!!");
//                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                } else {
//                                    ToastHelper.showCustomToast(getApplicationContext(), "Email không chính xác !!!");
//                                }
//                                progressBar.setVisibility(View.INVISIBLE);
//                            },
//                            throwable -> {
//                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.INVISIBLE);
//                            }
//                    ));
        }
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        editTextResetPassEmail = findViewById(R.id.editTextResetPassEmail);
        buttonResetPass = findViewById(R.id.buttonResetPass);
        progressBar = findViewById(R.id.progressbar);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
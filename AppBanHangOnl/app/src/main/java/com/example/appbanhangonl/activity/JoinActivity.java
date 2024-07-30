package com.example.appbanhangonl.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.appbanhangonl.R;
import com.example.appbanhangonl.model.NotiSendData;
import com.example.appbanhangonl.model.ToastHelper;
import com.example.appbanhangonl.retrofit.ApiBanHang;
import com.example.appbanhangonl.retrofit.ApiPushNotification;
import com.example.appbanhangonl.retrofit.RetrofitClient;
import com.example.appbanhangonl.retrofit.RetrofitClientNoti;
import com.example.appbanhangonl.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class JoinActivity extends AppCompatActivity {
    private static final int PERMISSION_REQ_ID = 22;
    // import android.Manifest;
    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    Toolbar toolbarCreateRoom;
    Button btnCreate;
    Button btnJoinHost;
    EditText etMeetingId;
    private String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlrZXkiOiI0OWNlMjM4MS0wMTBkLTRjNDctYjFiZS04MGI4YTg4NGQzZTUiLCJwZXJtaXNzaW9ucyI6WyJhbGxvd19qb2luIl0sImlhdCI6MTcxODA5NjkxOSwiZXhwIjoxODc1ODg0OTE5fQ.SO_HQ0xcT3ji_Le4WHisQTki_1OYFDvKijqeR0gkPy4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_join);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        btnCreate = findViewById(R.id.btnCreateMeeting);
        btnJoinHost = findViewById(R.id.btnJoinHostMeeting);
        etMeetingId = findViewById(R.id.etMeetingId);
        toolbarCreateRoom = findViewById(R.id.toolBarCreateRoom);

        ActionToolBar();

        checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID);
        checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID);

        // create meeting and join as Host
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMeeting(token);
                pushNotiToUser();
            }
        });

        // Join as Host
        btnJoinHost.setOnClickListener(v -> {
            Intent intent = new Intent(JoinActivity.this, MeetingActivity.class);
            intent.putExtra("token", token);
            intent.putExtra("meetingId", etMeetingId.getText().toString().trim());
            intent.putExtra("mode", "CONFERENCE");
            startActivity(intent);
        });
    }

    private void createMeeting(String token) {
        // thực hiện lệnh gọi API tới Máy chủ VideoSDK để lấy roomId
        AndroidNetworking.post("https://api.videosdk.live/v2/rooms")
                .addHeaders("Authorization", token) // chuyển mã thông báo trong Tiêu đề
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // phản hồi chứa `roomId`
                            final String meetingId = response.getString("roomId");

                            // bắt đầu MeetingActivity với roomId đã nhận và token
                            Intent intent = new Intent(JoinActivity.this, MeetingActivity.class);
                            intent.putExtra("token", token);
                            intent.putExtra("meetingId", meetingId);
                            intent.putExtra("mode", "CONFERENCE");
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        ToastHelper.showCustomToast(getApplicationContext(), anError.getMessage());
                    }
                });
    }

    private void checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarCreateRoom);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbarCreateRoom.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void pushNotiToUser() {
        compositeDisposable.add(apiBanHang.getAllToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> {
                            if (user.isSucces()) {
                                for (int i = 0; i < user.getResult().size(); i++) {
                                    Map<String, String> data = new HashMap<>();
                                    data.put("title", "Thông báo");
                                    data.put("body", "Hiện giờ đang có phiên livestream từ admin");
                                    NotiSendData notiSendData = new NotiSendData(user.getResult().get(i).getToken(), data);
                                    ApiPushNotification apiPushNotification = RetrofitClientNoti.getInstance().create(ApiPushNotification.class);
                                    compositeDisposable.add(apiPushNotification.senNotification(notiSendData)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    notiResponse -> {
                                                        ToastHelper.showCustomToast(getApplicationContext(), "Thành công !!!");
                                                    },
                                                    throwable -> {
                                                        ToastHelper.showCustomToast(getApplicationContext(), "Lỗi: " + throwable.getMessage());
                                                    }
                                            ));
                                }
                            } else {
                                ToastHelper.showCustomToast(getApplicationContext(), "Không nhận được thông báo !!!");
                                Log.d("- Lỗi", "Không nhận được thông báo");
                            }
                        },
                        throwable -> {
                            Log.d("Lỗi", "Lỗi: " + throwable.getMessage());
                        }
                ));
    }
}
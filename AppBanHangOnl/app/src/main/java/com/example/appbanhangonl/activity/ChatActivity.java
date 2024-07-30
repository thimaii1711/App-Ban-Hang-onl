package com.example.appbanhangonl.activity;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.appbanhangonl.R;
import com.example.appbanhangonl.adapter.ChatAdapter;
import com.example.appbanhangonl.model.ChatMessModel;
import com.example.appbanhangonl.model.NotiSendData;
import com.example.appbanhangonl.model.ToastHelper;
import com.example.appbanhangonl.retrofit.ApiBanHang;
import com.example.appbanhangonl.retrofit.ApiPushNotification;
import com.example.appbanhangonl.retrofit.RetrofitClient;
import com.example.appbanhangonl.retrofit.RetrofitClientNoti;
import com.example.appbanhangonl.utils.Utils;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChatActivity extends AppCompatActivity {

    FirebaseFirestore db;
    ChatAdapter chatAdpate;
    List<ChatMessModel> listmess;
    String lastmess;
    String lastid;
    RecyclerView recyclerView_chat;
    ImageView imgSend, btn_back_chat;
    EditText inputMess;
    //id người gửi người nhận
    String idsend, idreceive;
    //thông báo
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);

        idsend = String.valueOf(getIntent().getIntExtra("idsend", 0)); //id người gửi
        idreceive = String.valueOf(getIntent().getIntExtra("idreceive", 0)); //id người gửi
        initView();
        initControl();

        listenMess();

        ActionToolBar();

        //insertUser();       // thêm user đang dùng vào trong danh sách user của trang admin
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void insertUser() {     // thêm user đang dùng vào trong danh sách user của trang admin
        HashMap<String, Object> user = new HashMap<>();
        user.put("id", String.valueOf(Utils.user_current.getId()));
        user.put("username", Utils.user_current.getUsername());
        user.put("imageuser", Utils.user_current.getImageUser());
        user.put("lastmess", lastmess);
        user.put("lastid", lastid);
        user.put("lastdate", new Date());

        db.collection("users").document(String.valueOf(Utils.user_current.getId())).set(user);
    }

    private void initControl() {
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastmess = String.valueOf(inputMess.getText());
                lastid = String.valueOf(Utils.user_current.getId());
                sendMessToFirebase();
                if (Utils.user_current.getStatus() == 1)
                    pushNotiToUser();
                else {
                    if (Utils.user_current.getId() != 0)
                        insertUser();
                    pushNotiToAdmin();
                }
            }
        });
    }

    private void sendMessToFirebase() { //Gửi tin nhắn lên Firebase
        String mess = inputMess.getText().toString().trim();
        if (TextUtils.isEmpty(mess)) {

        } else {
            HashMap<String, Object> messHashMap = new HashMap<>();
            messHashMap.put(Utils.SENDID, idsend);
            messHashMap.put(Utils.RECEIVEDID, idreceive);
            messHashMap.put(Utils.MESS, mess);
            messHashMap.put(Utils.DATETIME, new Date());
            db.collection(Utils.PATH_CHAT).add(messHashMap);
            inputMess.setText("");
        }
    }

    private void listenMess() {
        db.collection(Utils.PATH_CHAT)
                .whereEqualTo(Utils.SENDID, idsend)
                .whereEqualTo(Utils.RECEIVEDID, idreceive)
                .addSnapshotListener(eventListener);

        db.collection(Utils.PATH_CHAT)
                .whereEqualTo(Utils.SENDID, idreceive)
                .whereEqualTo(Utils.RECEIVEDID, idsend)
                .addSnapshotListener(eventListener);
    }

    private final com.google.firebase.firestore.EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            Integer count = listmess.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    ChatMessModel chatmess = new ChatMessModel();
                    chatmess.sendid = documentChange.getDocument().getString(Utils.SENDID);
                    chatmess.receiveid = documentChange.getDocument().getString(Utils.RECEIVEDID);
                    chatmess.mess = documentChange.getDocument().getString(Utils.MESS);
                    chatmess.dateObj = documentChange.getDocument().getDate(Utils.DATETIME);
                    chatmess.datatime = fomat_date(documentChange.getDocument().getDate(Utils.DATETIME));
                    listmess.add(chatmess);
                }
            }

            Collections.sort(listmess, (obj1, obj2) -> obj1.dateObj.compareTo(obj2.dateObj));
            if (count == 0) {
                chatAdpate.notifyDataSetChanged();
            } else {
                chatAdpate.notifyItemRangeInserted(listmess.size(), listmess.size());
                recyclerView_chat.smoothScrollToPosition(listmess.size() - 1);
            }
        }
    };

    private void pushNotiToAdmin() {
        compositeDisposable.add(apiBanHang.gettoken(1, Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> {
                            if (user.isSucces()) {
                                for (int i = 0; i < user.getResult().size(); i++) {
                                    Map<String, String> data = new HashMap<>();
                                    data.put("title", Utils.user_current.getUsername());
                                    data.put("body", lastmess);
                                    NotiSendData notiSendData = new NotiSendData(user.getResult().get(i).getToken(), data);
                                    ApiPushNotification apiPushNotification = RetrofitClientNoti.getInstance().create(ApiPushNotification.class);
                                    compositeDisposable.add(apiPushNotification.senNotification(notiSendData)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    notiResponse -> {
                                                        ToastHelper.showCustomToast(getApplicationContext(), "Đã gửi CSKH");
                                                    },
                                                    throwable -> {
                                                        ToastHelper.showCustomToast(getApplicationContext(), "Lỗi: " + throwable.getMessage());
                                                    }
                                            ));
                                }
                            } else {
                                ToastHelper.showCustomToast(getApplicationContext(), "Không nhận được thông báo !!!");
                                Log.d("Notification - Lỗi", "Không nhận được thông báo");
                            }
                        },
                        throwable -> {
                            Log.d("Notification - Lỗi", "Lỗi: " + throwable.getMessage());
                        }
                ));
    }

    private void pushNotiToUser() {
        compositeDisposable.add(apiBanHang.gettoken(0, Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> {
                            if (user.isSucces()) {
                                for (int i = 0; i < user.getResult().size(); i++) {
                                    if (String.valueOf(user.getResult().get(i).getId()).equals(idreceive)) {
                                        int positon = i;
                                        Map<String, String> data = new HashMap<>();
                                        data.put("title", Utils.user_current.getUsername());
                                        data.put("body", lastmess);
                                        NotiSendData notiSendData = new NotiSendData(user.getResult().get(i).getToken(), data);
                                        ApiPushNotification apiPushNotification = RetrofitClientNoti.getInstance().create(ApiPushNotification.class);
                                        compositeDisposable.add(apiPushNotification.senNotification(notiSendData)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(
                                                        notiResponse -> {
                                                            ToastHelper.showCustomToast(getApplicationContext(), "Đã gửi tới " + user.getResult().get(positon).getUsername());
                                                        },
                                                        throwable -> {
                                                            ToastHelper.showCustomToast(getApplicationContext(), "Lỗi: " + throwable.getMessage());
                                                        }
                                                ));
                                    }
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

    private String fomat_date(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(date);
    }

    private void initView() {
        // Khởi tạo recyclerView_chat trước
        toolbar = findViewById(R.id.toolbar);
        recyclerView_chat = findViewById(R.id.recyclerView_chat);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        listmess = new ArrayList<>();
        chatAdpate = new ChatAdapter(getApplicationContext(), listmess, String.valueOf(idsend));

        // Đặt adapter sau khi đã khởi tạo recyclerView_chat
        recyclerView_chat.setAdapter(chatAdpate);

        db = FirebaseFirestore.getInstance();
        imgSend = findViewById(R.id.icon_sendchat);
        inputMess = findViewById(R.id.txt_inputtext);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_chat.setLayoutManager(layoutManager);
        recyclerView_chat.setHasFixedSize(true);
    }
}
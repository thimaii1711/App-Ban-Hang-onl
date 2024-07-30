package com.example.appbanhangonl.activity;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appbanhangonl.R;
import com.example.appbanhangonl.adapter.AdminChatAdapter;
import com.example.appbanhangonl.model.UserModel;
import com.example.appbanhangonl.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminChatActivity extends AppCompatActivity {

    AdminChatAdapter adapter;
    RecyclerView recyclerView_listuserchat;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Toolbar toolbar;
    // thêm chức năng lấy tin nhắn gần nhất
    String lastmess;
    String lastid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_chat);

        initView();
        getUserFromFirebase();
        ActionToolBar();
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

    private void getUserFromFirebase() {
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isComplete()) {
                            List<UserModel> userlist = new ArrayList<>();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if (Integer.parseInt(documentSnapshot.getString("id")) == Utils.user_current.getId())
                                    continue;
                                UserModel user = new UserModel();
                                user.setId(Integer.parseInt(documentSnapshot.getString("id")));
                                user.setUsername(documentSnapshot.getString("username"));
                                user.setImageUser(documentSnapshot.getString("imageuser"));
                                userlist.add(user);
                            }
                            if (userlist.size() > 0) {
                                adapter = new AdminChatAdapter(getApplicationContext(), userlist);
                                recyclerView_listuserchat.setAdapter(adapter);
                            }
                        }
                    }
                });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView_listuserchat = findViewById(R.id.recyclerView_listuserchat);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_listuserchat.setLayoutManager(layoutManager);
        recyclerView_listuserchat.setHasFixedSize(true);
    }
}
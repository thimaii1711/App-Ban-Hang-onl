package com.example.appbanhangonl.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.appbanhangonl.R;

public class PromotionActivity extends AppCompatActivity {
    TextView textViewInformation;
    ImageView imageViewPromotion;
    Toolbar toolbarPromotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_promotion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        initDatas();
    }

    private void initDatas() {
        String information = getIntent().getStringExtra("information");
        String url = getIntent().getStringExtra("url");
        textViewInformation.setText(information);
        Glide.with(this).load(url).into(imageViewPromotion);
    }

    private void initViews() {
        textViewInformation = findViewById(R.id.textViewInformation);
        imageViewPromotion = findViewById(R.id.imageViewPromotion);
        toolbarPromotion = findViewById(R.id.toolbarPromotion);

        setSupportActionBar(toolbarPromotion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPromotion.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
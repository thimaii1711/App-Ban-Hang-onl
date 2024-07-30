package com.example.appbanhangonl.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appbanhangonl.R;
import com.example.appbanhangonl.adapter.CategoryAdapter;
import com.example.appbanhangonl.adapter.ProductAdapter;
import com.example.appbanhangonl.model.CategoryModel;
import com.example.appbanhangonl.model.ProductModel;
import com.example.appbanhangonl.model.PromotionModel;
import com.example.appbanhangonl.model.ToastHelper;
import com.example.appbanhangonl.model.UserModel;
import com.example.appbanhangonl.model.ViewOrders;
import com.example.appbanhangonl.retrofit.ApiBanHang;
import com.example.appbanhangonl.retrofit.RetrofitClient;
import com.example.appbanhangonl.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewHome;
    NavigationView navigationView;
    ListView listViewHome;
    DrawerLayout drawerLayout;
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<ProductModel> productModelList;
    ProductAdapter productAdapter;
    NotificationBadge notificationBadge;
    FrameLayout frameLayout;
    ImageView imageViewSearch, imageView_HinhAnhUser, imageChat;
    TextView textView_TenNguoiDung;
    private static final int REQUEST_CODE_PROFILE = 1;
    ImageSlider imageSliderBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Paper.init(this);
        if (Paper.book().read("user") != null) {
            UserModel user = Paper.book().read("user");
            Utils.user_current = user;
        }
        getToken();
        Mapping();
        ActionBar();
        if (isConnected(this)) {
            ActionViewFliper();
            getCategoryList();
            getProduct();
            getEventClick();
        } else {
            ToastHelper.showCustomToast(this, "Không có Internet !!!");
        }

        ShowEmailNavigation();
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (!TextUtils.isEmpty(s)) {
                            compositeDisposable.add(apiBanHang.updateToken(Utils.user_current.getId(), s)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            messageModel -> {

                                            },
                                            throwable -> {
                                                Log.d("-Lỗi log: ", "- Lỗi: " + throwable.getMessage());
                                            }
                                    ));
                        }
                    }
                });

        //Chat cần cái này
        compositeDisposable.add(apiBanHang.gettoken(1, Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> {
                    if (userModel.isSucces()) {
                        Utils.ID_RECEIVED = "7";
                    }
                }, throwable -> {
                }));
    }

    private void getEventClick() {
        listViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String categoryName = categoryModelList.get(position).getTenSP();
                Intent intent;
                switch (categoryName) {
                    case "Trang chủ":
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        break;
                    case "Điện thoại":
                        intent = new Intent(getApplicationContext(), PhoneProductActivity.class);
                        intent.putExtra("Loai", 1);
                        break;
                    case "Lap Top":
                        intent = new Intent(getApplicationContext(), PhoneProductActivity.class);
                        intent.putExtra("Loai", 2);
                        break;
                    case "Đơn hàng":
                        if (Utils.user_current.getStatus() == 1) {
                            intent = new Intent(getApplicationContext(), ViewOrdersActivity.class);
                        } else {
                            intent = new Intent(getApplicationContext(), ViewOrdersUserActivity.class);
                        }
                        break;
                    case "Liên hệ":
                        intent = new Intent(getApplicationContext(), ContactActivity.class);
                        break;
                    case "Thông tin":
                        intent = new Intent(getApplicationContext(), InfomationActivity.class);
                        break;
                    case "Quản lý":
                        intent = new Intent(getApplicationContext(), ManagementActivity.class);
                        break;
                    case "Thống kê":
                        intent = new Intent(getApplicationContext(), ThongKeActivity.class);
                        break;
                    case "Live":
                        intent = new Intent(getApplicationContext(), JoinActivity.class);
                        break;
                    case "Xem Live":
                        intent = new Intent(getApplicationContext(), MeetingUserActivity.class);
                        break;
                    case "Đăng xuất":
                        Paper.book().delete("user");
                        FirebaseAuth.getInstance().signOut();
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        break;
                    default:
                        return;
                }

                startActivity(intent);
            }
        });
    }

    private void getProduct() {
        compositeDisposable.add(apiBanHang.getAPIProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        product -> {
                            if (product.isSucces()) {
                                productModelList = product.getResult();
                                productAdapter = new ProductAdapter(getApplicationContext(), productModelList);
                                recyclerViewHome.setAdapter(productAdapter);
                            }
                        },
                        throwable -> {
                            ToastHelper.showCustomToast(this, "Không kết nối được" + throwable.getMessage());
                        }
                ));
    }

    private void getCategoryList() {
        compositeDisposable.add(apiBanHang.getAPICategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        category -> {
                            if (category.isSucces()) {
                                categoryModelList = category.getResult();

                                // Thêm các mục không bị ẩn bất kể trạng thái tài khoản
                                categoryModelList.add(new CategoryModel("Đơn hàng", "orther.png"));
                                categoryModelList.add(new CategoryModel("Liên hệ", "contact.png"));
                                categoryModelList.add(new CategoryModel("Thông tin", "info.png"));

                                // Kiểm tra trạng thái tài khoản
                                if (Utils.user_current.getStatus() != 0) {
                                    categoryModelList.add(new CategoryModel("Quản lý", "management.png"));
                                    categoryModelList.add(new CategoryModel("Thống kê", "statistical.png"));
                                    categoryModelList.add(new CategoryModel("Live", "livestream.png"));
                                }

                                categoryModelList.add(new CategoryModel("Xem Live", "live.png"));
                                categoryModelList.add(new CategoryModel("Đăng xuất", "out.png"));
                                categoryAdapter = new CategoryAdapter(getApplicationContext(), categoryModelList);
                                listViewHome.setAdapter(categoryAdapter);
                            }
                        },
                        throwable -> {
                            // Handle error
                        }
                ));
    }

    private void ShowEmailNavigation() {
        // Lấy header view từ NavigationView
        View headerView = navigationView.getHeaderView(0);

        // Tìm TextView trong header view
        textView_TenNguoiDung = headerView.findViewById(R.id.textView_TenNguoiDung);
        imageView_HinhAnhUser = headerView.findViewById(R.id.imageView_HinhAnhUser);

        if (textView_TenNguoiDung != null) {
            // Hiển thị email trong TextView
            textView_TenNguoiDung.setText(Utils.user_current.getUsername());
            Glide.with(getApplicationContext()).load(Utils.user_current.getImageUser()).into(imageView_HinhAnhUser);
            imageView_HinhAnhUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    intent.putExtra("Email", Utils.user_current.getEmail());
                    intent.putExtra("Username", Utils.user_current.getUsername());
                    intent.putExtra("Mobile", Utils.user_current.getMobile());
                    intent.putExtra("ImageUser", Utils.user_current.getImageUser());
                    startActivityForResult(intent, REQUEST_CODE_PROFILE);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Không tìm thấy TextView", Toast.LENGTH_SHORT).show();
        }
    }

    //Ánh xạ
    private void Mapping() {
        toolbar = findViewById(R.id.toolBarHome);
        imageSliderBanner = findViewById(R.id.imageSliderBanner);
        recyclerViewHome = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerViewHome.setLayoutManager(layoutManager);
        recyclerViewHome.setHasFixedSize(true);
        navigationView = findViewById(R.id.navigationView);
        listViewHome = findViewById(R.id.listViewHome);
        drawerLayout = findViewById(R.id.drawerLayout);
        notificationBadge = findViewById(R.id.menu_quanlity);
        imageViewSearch = findViewById(R.id.imageSearch);
        imageChat = findViewById(R.id.imageChat);
        //Khởi tạo list
        categoryModelList = new ArrayList<>();
        productModelList = new ArrayList<>();
        frameLayout = findViewById(R.id.frameCart);
        if (Utils.CartList == null) {
            Utils.CartList = new ArrayList<>();
        } else {
            int totalItem = 0;
            for (int i = 0; i < Utils.CartList.size(); i++) {
                totalItem = totalItem + Utils.CartList.get(i).getQuality();
            }
            notificationBadge.setText(String.valueOf(totalItem));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(cart);
            }
        });

        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        imageChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.user_current.getStatus() == 1)    //nếu là admin vào
                { // tài khoản status == 1 là admin chat
                    Intent intent = new Intent(getApplicationContext(), AdminChatActivity.class);
                    // id== 7 dùng để chat, nhiều admin có thể cùng xử lý 1 khách hàng
                    intent.putExtra("idsend", 12);
                    startActivity(intent);
                } else    //nếu không phải admin
                {
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    intent.putExtra("idsend", Utils.user_current.getId());
                    // id== 7 dùng để chat, nhiều admin có thể cùng xử lý 1 khách hàng
                    intent.putExtra("idreceive", 12);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for (int i = 0; i < Utils.CartList.size(); i++) {
            totalItem = totalItem + Utils.CartList.get(i).getQuality();
        }
        notificationBadge.setText(String.valueOf(totalItem));
    }

    //Thanh menu
    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size); //Thay đổi icon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    //Chạy quảng cáo
    private void ActionViewFliper() {
        List<SlideModel> slideModels = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Utils.BASE_URL + "promotion.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("succes")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        ArrayList<PromotionModel> promotionModels = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObjectPromotion = jsonArray.getJSONObject(i);
                            promotionModels.add(new PromotionModel(jsonObjectPromotion.getInt("id"), jsonObjectPromotion.getString("url"), jsonObjectPromotion.getString("information")));
                            slideModels.add(new SlideModel(jsonArray.getJSONObject(i).getString("url"), null));
                        }
                        imageSliderBanner.setImageList(slideModels, ScaleTypes.FIT);

                        imageSliderBanner.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onItemSelected(int i) {
                                Intent intent = new Intent(getApplicationContext(), PromotionActivity.class);
                                intent.putExtra("information", promotionModels.get(i).getInformation());
                                intent.putExtra("url", promotionModels.get(i).getUrl());
                                startActivity(intent);
                            }

                            @Override
                            public void doubleClick(int i) {

                            }
                        });
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Không kết nối được", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    //Kiểm tra kết nối Internet
    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PROFILE && resultCode == RESULT_OK) {
            // Cập nhật giao diện với thông tin người dùng mới
            ShowEmailNavigation();
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
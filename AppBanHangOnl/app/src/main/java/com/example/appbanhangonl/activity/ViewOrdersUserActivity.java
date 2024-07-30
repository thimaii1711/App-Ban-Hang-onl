package com.example.appbanhangonl.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangonl.Interface.ItemClickDeleteListener;
import com.example.appbanhangonl.R;
import com.example.appbanhangonl.adapter.ViewOrdersUserAdapter;
import com.example.appbanhangonl.model.ToastHelper;
import com.example.appbanhangonl.retrofit.ApiBanHang;
import com.example.appbanhangonl.retrofit.RetrofitClient;
import com.example.appbanhangonl.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ViewOrdersUserActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    RecyclerView recyclerViewOrdersUser;
    Toolbar toolbarOrdersUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_orders_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        initToolbar();
        getOrdersUser();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getOrdersUser() {
        compositeDisposable.add(apiBanHang.viewordersAPI(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        viewOrders -> {
                            ViewOrdersUserAdapter adapter = new ViewOrdersUserAdapter(getApplicationContext(), viewOrders.getResult(), new ItemClickDeleteListener() {
                                @Override
                                public void onClickDelete(int idOrder, int status, View v) {
                                    showDeleteOrder(idOrder, status, v);
                                }
                            });
                            recyclerViewOrdersUser.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        },
                        throwable -> {

                        }
                ));
    }

    private void showDeleteOrder(int idOrder, int status, View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.menu_delete);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.itemDeleteOrder) {
                    deleteOrder(idOrder, status);
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void deleteOrder(int idOrder, int status) {
        if (status == 2) {
            ToastHelper.showCustomToast(getApplicationContext(), "Đơn hàng đã giao cho đơn vị vận chuyển. Không thể hủy đơn hàng");
            return;
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.BASE_URL + "deleteorder.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getBoolean("succes")) {
                        ToastHelper.showCustomToast(ViewOrdersUserActivity.this, "Xóa đơn hàng thành công");
                        getOrdersUser();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ViewOrdersUserActivity.this, "Lỗi kết nối đến server", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idOrder", String.valueOf(idOrder));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void initToolbar() {
        setSupportActionBar(toolbarOrdersUser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarOrdersUser.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbarOrdersUser = findViewById(R.id.toolbarOrdersUser);
        recyclerViewOrdersUser = findViewById(R.id.recyclerViewOrdersUser);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewOrdersUser.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
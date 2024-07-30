package com.example.appbanhangonl.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhangonl.R;
import com.example.appbanhangonl.retrofit.ApiBanHang;
import com.example.appbanhangonl.retrofit.RetrofitClient;
import com.example.appbanhangonl.utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThongKeActivity extends AppCompatActivity {

    Toolbar toolbar;
    PieChart pieChart;
    BarChart barChart;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    private static final String CHART_TITLE_MONTHLY = "Doanh thu theo tháng";
    private static final String CHART_TITLE_BEST_SELLING = "Sản phẩm bán chạy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initview();
        ActionToolBar();
        getdataChart();
        settingBarchart();
    }

    private void settingBarchart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false);
        barChart.setFitBars(true);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(12);
        xAxis.setLabelCount(12);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12f);

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setAxisMinimum(0);
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawLabels(false);

        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0);
        yAxisLeft.setTextColor(Color.BLACK);
        yAxisLeft.setTextSize(12f);
        yAxisLeft.setGranularity(1f);

        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(12f);

        barChart.animateXY(2000, 2000);
        barChart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thongke, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.tkthang) {
            getTkThang();
            barChart.setVisibility(View.VISIBLE);
            pieChart.setVisibility(View.GONE); // Hide pie chart
            updateTitle(CHART_TITLE_MONTHLY);
            return true;
        } else if (id == R.id.tkbanchay) {
            getdataChart();
            pieChart.setVisibility(View.VISIBLE);
            barChart.setVisibility(View.GONE); // Hide bar chart
            updateTitle(CHART_TITLE_BEST_SELLING);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void updateTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void getTkThang() {
        barChart.setVisibility(View.VISIBLE);
        pieChart.setVisibility(View.GONE);
        compositeDisposable.add(apiBanHang.getthongkeThang()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeModel -> {
                            if (thongKeModel.isSucces()) {
                                List<BarEntry> listdata = new ArrayList<>();
                                for (int i = 0; i < thongKeModel.getResult().size(); i++) {
                                    String tongtien = String.valueOf(thongKeModel.getResult().get(i).getTongtienthang());
                                    String thang = thongKeModel.getResult().get(i).getThang();
                                    listdata.add(new BarEntry(Integer.parseInt(thang), Float.parseFloat(tongtien)));
                                }
                                BarDataSet barDataSet = new BarDataSet(listdata, "Tổng tiền");
                                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                barDataSet.setValueTextSize(14f);
                                barDataSet.setValueTextColor(Color.RED);

                                BarData data = new BarData(barDataSet);
                                barChart.animateXY(2000, 2000);
                                barChart.setData(data);
                                barChart.invalidate();
                            }
                        },
                        throwable -> {
                            Log.d("logg", throwable.getMessage());
                        }
                )
        );
    }

    private void getdataChart() {
        updateTitle(CHART_TITLE_BEST_SELLING);
        List<PieEntry> listdata = new ArrayList<>();
        List<String> productNames = new ArrayList<>(); // Danh sách tên sản phẩm
        Map<String, Integer> productPrices = new HashMap<>(); // Map to store product prices
        Map<String, Integer> productQuantities = new HashMap<>(); // Map to store product quantities

        compositeDisposable.add(apiBanHang.getthongke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeModel -> {
                            if (thongKeModel.isSucces()) {
                                int total = 0;
                                for (int i = 0; i < thongKeModel.getResult().size(); i++) {
                                    int tong = thongKeModel.getResult().get(i).getTong();
                                    total += tong;
                                    String tenSP = thongKeModel.getResult().get(i).getTenSP();
                                    int giaSP = thongKeModel.getResult().get(i).getGiaSP(); // Get product price
                                    productNames.add(tenSP); // Thêm tên sản phẩm vào danh sách
                                    productPrices.put(tenSP, giaSP); // Add product price to map
                                    productQuantities.put(tenSP, tong); // Add product quantity to map
                                    listdata.add(new PieEntry(tong, tenSP)); // Include product name in PieEntry
                                }
                                PieDataSet pieDataSet = new PieDataSet(listdata, "");
                                PieData data = new PieData(pieDataSet);
                                data.setValueTextSize(12f);
                                data.setValueFormatter(new PercentFormatter(pieChart));
                                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                                pieChart.setData(data);
                                pieChart.animateXY(2000, 2000);
                                pieChart.setUsePercentValues(true);
                                pieChart.getDescription().setEnabled(false);
                                pieChart.invalidate();

                                final float finalTotal = total;
                                pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                                    @Override
                                    public void onValueSelected(Entry e, Highlight h) {
                                        PieEntry selectedEntry = (PieEntry) e;
                                        int selectedValue = (int) selectedEntry.getValue();
                                        float percentage = (selectedValue / finalTotal) * 100;

                                        String productName = selectedEntry.getLabel(); // Get product name from label
                                        if (productQuantities.containsKey(productName) && productPrices.containsKey(productName)) {
                                            int totalQuantity = productQuantities.get(productName); // Get product quantity
                                            int productPrice = productPrices.get(productName); // Get product price
                                            int totalSalesAmount = totalQuantity * productPrice; // Calculate total sales amount
                                            showDetailInfo(productName, selectedValue, percentage, totalSalesAmount);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected() {

                                    }
                                });
                            }
                        },
                        throwable -> {
                            Log.d("logg", throwable.getMessage());
                        }
                ));
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

    private void initview() {
        toolbar = findViewById(R.id.toolbar);
        pieChart = findViewById(R.id.piechart);
        barChart = findViewById(R.id.barchart);
    }

    private void showDetailInfo(String productName, int selectedValue, float percentage, int totalSalesAmount) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ThongKeActivity.this);
        bottomSheetDialog.setContentView(R.layout.layout_detail_info);

        TextView txtProductName = bottomSheetDialog.findViewById(R.id.txt_product_name);
        TextView txtSelectedValue = bottomSheetDialog.findViewById(R.id.txt_selected_value);
        TextView txtPercentage = bottomSheetDialog.findViewById(R.id.txt_percentage);
        TextView txtSoluongSp = bottomSheetDialog.findViewById(R.id.txt_soluongsanpham);
        TextView txtTotalSales = bottomSheetDialog.findViewById(R.id.txt_total_sales);

        txtProductName.setText(productName);
        txtSelectedValue.setText("Tổng số lượng sản phẩm đã bán: " + selectedValue + " sản phẩm");
        txtPercentage.setText("Phần trăm: " + String.format("%.2f", percentage) + "%");
        txtSoluongSp.setText("Tổng số lượng sản phẩm có trong kho: " + selectedValue + " sản phẩm");

        // Định dạng số tiền theo kiểu VND
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedTotalSalesAmount = formatter.format(totalSalesAmount) + " VND";
        txtTotalSales.setText("Tổng số tiền thu được: " + formattedTotalSalesAmount);

        bottomSheetDialog.show();
    }
}
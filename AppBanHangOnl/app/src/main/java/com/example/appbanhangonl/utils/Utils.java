package com.example.appbanhangonl.utils;

import com.example.appbanhangonl.model.CartModel;
import com.example.appbanhangonl.model.UserModel;
import com.example.appbanhangonl.retrofit.ApiBanHang;
import com.example.appbanhangonl.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    //public static final  String BASE_URL = "http://tcshop.id.vn/banhang/";
    public static final String BASE_URL = "http://192.168.2.241:8081/banhang/";
    public static List<CartModel> CartList = new ArrayList<>();
    public static List<CartModel> CartListBuy = new ArrayList<>();
    public static UserModel user_current = new UserModel();

    public static String ID_RECEIVED;
    public static final String SENDID = "idsend";
    public static final String RECEIVEDID = "idrecieved";
    public static final String MESS = "message";
    public static final String DATETIME = "datetime";
    public static final String PATH_CHAT = "chat";

    public static ApiBanHang getData() {
        return RetrofitClient.getInstance(BASE_URL).create(ApiBanHang.class);
    }
}

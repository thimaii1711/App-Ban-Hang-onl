package com.example.appbanhangonl.model;

import java.util.List;

public class OrdersModel {
    int id;
    int iduser;
    String diachi;
    String sodienthoai;
    String tongtien;
    int trangthai;
    String username;
    List<ViewOrdersModel> item;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ViewOrdersModel> getItem() {
        return item;
    }

    public void setItem(List<ViewOrdersModel> item) {
        this.item = item;
    }

    public OrdersModel(int id, int iduser, String diachi, String sodienthoai, String tongtien, int trangthai, String username, List<ViewOrdersModel> item) {
        this.id = id;
        this.iduser = iduser;
        this.diachi = diachi;
        this.sodienthoai = sodienthoai;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
        this.username = username;
        this.item = item;
    }
}

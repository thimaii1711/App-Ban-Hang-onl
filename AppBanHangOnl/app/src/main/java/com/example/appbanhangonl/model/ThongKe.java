package com.example.appbanhangonl.model;

public class ThongKe {
    private String TenSP;
    private int tong;
    private String thang;
    private String tongtienthang;
    private int soluong;
    private int tongtien;
    private int GiaSP;

    public ThongKe(String tenSP, int tong, String thang, String tongtienthang, int soluong, int tongtien, int giaSP) {
        TenSP = tenSP;
        this.tong = tong;
        this.thang = thang;
        this.tongtienthang = tongtienthang;
        this.soluong = soluong;
        this.tongtien = tongtien;
        GiaSP = giaSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public int getTong() {
        return tong;
    }

    public void setTong(int tong) {
        this.tong = tong;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getTongtienthang() {
        return tongtienthang;
    }

    public void setTongtienthang(String tongtienthang) {
        this.tongtienthang = tongtienthang;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public int getGiaSP() {
        return GiaSP;
    }

    public void setGiaSP(int giaSP) {
        GiaSP = giaSP;
    }
// Constructor

}

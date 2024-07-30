package com.example.appbanhangonl.model;

import java.util.List;

public class ThongKeModel {
    boolean succes;
    String message;

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ThongKe> getResult() {
        return result;
    }

    public void setResult(List<ThongKe> result) {
        this.result = result;
    }

    List<ThongKe> result;
}

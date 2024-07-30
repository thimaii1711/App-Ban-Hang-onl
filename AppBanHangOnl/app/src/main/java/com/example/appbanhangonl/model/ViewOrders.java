package com.example.appbanhangonl.model;

import java.util.List;

public class ViewOrders {
    boolean succes;
    String message;
    List<OrdersModel> result;

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

    public List<OrdersModel> getResult() {
        return result;
    }

    public void setResult(List<OrdersModel> result) {
        this.result = result;
    }
}

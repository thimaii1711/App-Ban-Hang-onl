package com.example.appbanhangonl.model;

public class PromotionModel {
    private int id;
    private String url;
    private String information;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public PromotionModel(int id, String url, String information) {
        this.id = id;
        this.url = url;
        this.information = information;
    }
}

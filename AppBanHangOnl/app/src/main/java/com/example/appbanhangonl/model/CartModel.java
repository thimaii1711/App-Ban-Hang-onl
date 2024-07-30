package com.example.appbanhangonl.model;

public class CartModel {
    int cartid;
    String productName;
    long price;
    String productImg;
    int quality;
    private boolean isSelected;
    int quantityInStock;

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public CartModel(){}
    public CartModel(int cartid, String productName, long price, String productImg, int quality) {
        this.cartid = cartid;
        this.productName = productName;
        this.price = price;
        this.productImg = productImg;
        this.quality = quality;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public int getCartid() {
        return cartid;
    }

    public void setCartid(int cartid) {
        this.cartid = cartid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}

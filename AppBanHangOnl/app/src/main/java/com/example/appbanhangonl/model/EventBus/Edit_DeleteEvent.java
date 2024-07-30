package com.example.appbanhangonl.model.EventBus;

import com.example.appbanhangonl.model.ProductModel;

public class Edit_DeleteEvent {
    ProductModel productNew;

    public Edit_DeleteEvent(ProductModel productNew) {
        this.productNew = productNew;
    }

    public ProductModel getProductNew() {
        return productNew;
    }

    public void setProductNew(ProductModel productNew) {
        this.productNew = productNew;
    }
}

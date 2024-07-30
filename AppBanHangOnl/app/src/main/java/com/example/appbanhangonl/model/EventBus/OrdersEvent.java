package com.example.appbanhangonl.model.EventBus;

import com.example.appbanhangonl.model.OrdersModel;

public class OrdersEvent {
    // 2001210289 - Huỳnh Công Huy - Bài 46: Update tình trạng đơn hàng từ app quản lí
    OrdersModel order;

    public OrdersEvent(OrdersModel order) {
        this.order = order;
    }

    public OrdersModel getOrder() {
        return order;
    }

    public void setOrder(OrdersModel order) {
        this.order = order;
    }
}

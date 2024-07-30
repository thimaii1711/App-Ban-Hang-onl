package com.example.appbanhangonl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhangonl.Interface.ItemClickDeleteListener;
import com.example.appbanhangonl.R;
import com.example.appbanhangonl.model.OrdersModel;

import java.util.List;

public class ViewOrdersUserAdapter extends RecyclerView.Adapter<ViewOrdersUserAdapter.MyViewHolder> {
    private Context context;
    private List<OrdersModel> ordersModels;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ItemClickDeleteListener itemClickDeleteListener;

    public ViewOrdersUserAdapter(Context context, List<OrdersModel> ordersModels, ItemClickDeleteListener itemClickDeleteListener) {
        this.context = context;
        this.ordersModels = ordersModels;
        this.itemClickDeleteListener = itemClickDeleteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_order_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrdersModel ordersModel = ordersModels.get(position);
        holder.textViewOrderId.setText("Đơn hàng: " + ordersModel.getId());
        holder.textViewStatus.setText(orderStatus(ordersModel.getTrangthai()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.recyclerViewDetail.getContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setInitialPrefetchItemCount(ordersModel.getItem().size());

        ViewOrdersDetailsUserAdapter viewOrdersDetailsUserAdapter = new ViewOrdersDetailsUserAdapter(context, ordersModel.getItem());

        holder.recyclerViewDetail.setLayoutManager(linearLayoutManager);
        holder.recyclerViewDetail.setAdapter(viewOrdersDetailsUserAdapter);
        holder.recyclerViewDetail.setRecycledViewPool(viewPool);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemClickDeleteListener.onClickDelete(ordersModel.getId(), ordersModel.getTrangthai(), v);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return ordersModels.size();
    }

    private String orderStatus(int status) {
        String result = "";

        switch (status) {
            case 0:
                result = "Đơn hàng đang được xử lí";
                break;
            case 1:
                result = "Đơn hàng đã chấp nhận";
                break;
            case 2:
                result = "Đơn hàng đã giao cho đơn vị vận chuyển";
                break;
            case 3:
                result = "Thành công";
                break;
            case 4:
                result = "Đơn hàng đã hủy";
                break;
            default:
                result = "Đơn hàng đang được xử lí";
                break;
        }

        return result;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOrderId, textViewStatus;
        RecyclerView recyclerViewDetail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            recyclerViewDetail = itemView.findViewById(R.id.recyclerViewDetail);
        }
    }
}

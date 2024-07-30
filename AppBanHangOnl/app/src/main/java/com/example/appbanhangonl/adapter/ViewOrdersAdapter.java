package com.example.appbanhangonl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhangonl.Interface.ItemClickListener;
import com.example.appbanhangonl.R;
import com.example.appbanhangonl.model.EventBus.OrdersEvent;
import com.example.appbanhangonl.model.OrdersModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ViewOrdersAdapter extends RecyclerView.Adapter<ViewOrdersAdapter.MyViewHolder> {
    Context context;
    List<OrdersModel> list;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public ViewOrdersAdapter(Context context, List<OrdersModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vieworders, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrdersModel ordersModel = list.get(position);
        holder.textViewOrders.setText("Đơn hàng: " + ordersModel.getId());
        holder.textViewAddress.setText("Địa chỉ: " + ordersModel.getDiachi());
        holder.textViewUsername.setText("Người đặt: " + ordersModel.getUsername());
        holder.status.setText(orderStatus(ordersModel.getTrangthai()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerViewDetails.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(ordersModel.getItem().size());
        // adapter chitiet
        ViewOrdersDetailsAdapter viewOrdersDetailsAdapter = new ViewOrdersDetailsAdapter(context, ordersModel.getItem());
        holder.recyclerViewDetails.setLayoutManager(layoutManager);
        holder.recyclerViewDetails.setAdapter(viewOrdersDetailsAdapter);
        holder.recyclerViewDetails.setRecycledViewPool(viewPool);
        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (isLongClick) {
                    EventBus.getDefault().postSticky(new OrdersEvent(ordersModel));
                }
            }
        });
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView textViewOrders, status, textViewAddress, textViewUsername;
        RecyclerView recyclerViewDetails;
        ItemClickListener listener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrders = itemView.findViewById(R.id.idvieworders);
            status = itemView.findViewById(R.id.state);
            recyclerViewDetails = itemView.findViewById(R.id.recyclerView_details);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            itemView.setOnLongClickListener(this);
        }

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onClick(view, getAdapterPosition(), true);
            return false;
        }
    }
}

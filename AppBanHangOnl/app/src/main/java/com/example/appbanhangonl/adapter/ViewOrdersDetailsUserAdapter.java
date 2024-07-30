package com.example.appbanhangonl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhangonl.R;
import com.example.appbanhangonl.model.ViewOrdersModel;
import com.example.appbanhangonl.utils.Utils;

import java.util.List;

public class ViewOrdersDetailsUserAdapter extends RecyclerView.Adapter<ViewOrdersDetailsUserAdapter.MyViewHolder> {
    private Context context;
    private List<ViewOrdersModel> viewOrdersModels;

    public ViewOrdersDetailsUserAdapter(Context context, List<ViewOrdersModel> viewOrdersModels) {
        this.context = context;
        this.viewOrdersModels = viewOrdersModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_order_detail_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ViewOrdersModel viewOrdersModel = viewOrdersModels.get(position);
        holder.textViewProductName.setText(viewOrdersModel.getTenSP());
        holder.textViewProductQuantity.setText("Số lượng: " + viewOrdersModel.getSoluong());

        if (viewOrdersModel.getHinhAnh().contains("http")) {
            Glide.with(context).load(viewOrdersModel.getHinhAnh()).into(holder.imageViewProduct);
        } else {
            String Img = Utils.BASE_URL + "images/" + viewOrdersModel.getHinhAnh();
            Glide.with(context).load(Img).into(holder.imageViewProduct);
        }
    }

    @Override
    public int getItemCount() {
        return viewOrdersModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProduct;
        TextView textViewProductName, textViewProductQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewProductQuantity = itemView.findViewById(R.id.textViewProductQuantity);
        }
    }
}

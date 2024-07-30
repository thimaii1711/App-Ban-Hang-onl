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

public class ViewOrdersDetailsAdapter extends RecyclerView.Adapter<ViewOrdersDetailsAdapter.MyViewHolder> {

    Context context;
    List<ViewOrdersModel> viewOrdersModelList;

    public ViewOrdersDetailsAdapter(Context context, List<ViewOrdersModel> viewOrdersModelList) {
        this.context = context;
        this.viewOrdersModelList = viewOrdersModelList;
    }

    public ViewOrdersDetailsAdapter(List<ViewOrdersModel> viewOrdersModelList) {
        this.viewOrdersModelList = viewOrdersModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewordersdetails, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ViewOrdersModel viewOrdersModel = viewOrdersModelList.get(position);
        holder.textViewName.setText(viewOrdersModel.getTenSP());
        holder.textViewNum.setText(String.valueOf("Số lượng: " + viewOrdersModel.getSoluong()));

        if (viewOrdersModel.getHinhAnh().contains("http")) {
            Glide.with(context).load(viewOrdersModel.getHinhAnh()).into(holder.imageViewDetails);
        } else {
            String Img = Utils.BASE_URL + "images/" + viewOrdersModel.getHinhAnh();
            Glide.with(context).load(Img).into(holder.imageViewDetails);
        }

        //Glide.with(context).load(viewOrdersModel.getHinhAnh()).into(holder.imageViewDetails);
    }

    @Override
    public int getItemCount() {
        return viewOrdersModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewDetails;
        TextView textViewName, textViewNum;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewDetails = itemView.findViewById(R.id.item_detail_img);
            textViewName = itemView.findViewById(R.id.item_nameproduct);
            textViewNum = itemView.findViewById(R.id.item_num);
        }
    }
}

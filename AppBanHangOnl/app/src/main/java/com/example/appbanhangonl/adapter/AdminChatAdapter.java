package com.example.appbanhangonl.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhangonl.Interface.ItemClickListener;
import com.example.appbanhangonl.R;
import com.example.appbanhangonl.activity.ChatActivity;
import com.example.appbanhangonl.model.UserModel;
import com.example.appbanhangonl.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AdminChatAdapter extends RecyclerView.Adapter<AdminChatAdapter.MyViewHoder> {
    Context context;
    List<UserModel> listuser = new ArrayList<>();

    public AdminChatAdapter(Context context, List<UserModel> listuser) {
        this.context = context;
        this.listuser = listuser;
    }

    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_adminchat, parent, false);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
        UserModel user = listuser.get(position);

        holder.id_user_adminchat.setText(String.valueOf(user.getId()));
        holder.name_user_adminchat.setText(user.getUsername());
        //load ảnh
        if (user.getImageUser().contains("http")) {
            Glide.with(context).load(user.getImageUser()).into(holder.img_user_adminchat);
        } else {
            String Img = Utils.BASE_URL + "images/" + user.getImageUser();
            Glide.with(context).load(Img).into(holder.img_user_adminchat);
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (!isLongClick) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("idreceive", user.getId());
                    // id== 7 dùng để chat, nhiều admin có thể cùng xử lý 1 khách hàng
                    intent.putExtra("idsend", 12);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listuser.size();
    }

    public class MyViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id_user_adminchat, name_user_adminchat;
        ImageView img_user_adminchat;
        ItemClickListener itemClickListener;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            id_user_adminchat = itemView.findViewById(R.id.id_user_adminchat);
            name_user_adminchat = itemView.findViewById(R.id.name_user_adminchat);
            img_user_adminchat = itemView.findViewById(R.id.img_user_adminchat);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}

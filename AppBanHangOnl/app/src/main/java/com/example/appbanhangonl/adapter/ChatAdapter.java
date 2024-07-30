package com.example.appbanhangonl.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhangonl.R;
import com.example.appbanhangonl.model.ChatMessModel;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private android.content.Context context;
    private List<ChatMessModel> chatMessageList;
    private String sendid;
    private static final Integer TYPE_SEND = 1;
    private static final Integer TYPE_RECEIVE = 2;

    public ChatAdapter(android.content.Context context, List<ChatMessModel> chatMessageList, String sendid) {
        this.context = context;
        this.chatMessageList = chatMessageList;
        this.sendid = sendid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_SEND){
            view = LayoutInflater.from(context).inflate(R.layout.item_send_chat,parent,false);
            return new SendMessViewHolder(view);
        }else
        {
            view = LayoutInflater.from(context).inflate(R.layout.item_receive_chat,parent,false);
            return new ReceiveMessViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_SEND){
            ((SendMessViewHolder) holder).txt_mess.setText(chatMessageList.get(position).mess);
            ((SendMessViewHolder) holder).txt_time.setText(chatMessageList.get(position).datatime);
        }else {
            ((ReceiveMessViewHolder) holder).txt_mess.setText(chatMessageList.get(position).mess);
            ((ReceiveMessViewHolder) holder).txt_time.setText(chatMessageList.get(position).datatime);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessageList.get(position).sendid.equals(sendid)){
            return TYPE_SEND;
        }else {
            return TYPE_RECEIVE;
        }
    }

    class SendMessViewHolder extends RecyclerView.ViewHolder{
        TextView txt_mess, txt_time;
        public SendMessViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_mess = itemView.findViewById(R.id.txt_mess_send);
            txt_time = itemView.findViewById(R.id.txt_time_send);
        }
    }

    class ReceiveMessViewHolder extends RecyclerView.ViewHolder{
        TextView txt_mess, txt_time;
        public ReceiveMessViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_mess = itemView.findViewById(R.id.txt_mess_receive);
            txt_time = itemView.findViewById(R.id.txt_time_receive);
        }
    }
}

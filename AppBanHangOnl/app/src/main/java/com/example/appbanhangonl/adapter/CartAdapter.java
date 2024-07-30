package com.example.appbanhangonl.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhangonl.Interface.ImageClickListenner;
import com.example.appbanhangonl.R;
import com.example.appbanhangonl.model.CartModel;
import com.example.appbanhangonl.model.EventBus.totalAmountEvent;
import com.example.appbanhangonl.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<CartModel> cartModelList;

    // Đặt biến để kiểm tra xem danh sách đã thay đổi hay chưa
    boolean cartListChanged = false;

    public CartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartModel cartModel = cartModelList.get(position);
        holder.item_cart_name.setText(cartModel.getProductName());
        holder.item_cart_quanlity.setText(cartModel.getQuality() + " ");

        if (cartModel.getProductImg().contains("http")) {
            Glide.with(context).load(cartModel.getProductImg()).into(holder.item_cart_img);
        } else {
            String Img = Utils.BASE_URL + "images/" + cartModel.getProductImg();
            Glide.with(context).load(Img).into(holder.item_cart_img);
        }

        //Glide.with(context).load(cartModel.getProductImg()).into(holder.item_cart_img);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_cart_price.setText("Giá: " + decimalFormat.format(cartModel.getPrice()));
        long price = cartModel.getQuality() * cartModel.getPrice();
        holder.item_cart_priceproduct.setText("Tiền: " + decimalFormat.format(price));
        holder.item_cart_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Utils.CartListBuy.add(cartModel);
                    EventBus.getDefault().postSticky(new totalAmountEvent());
                    cartListChanged = true; // Đánh dấu rằng danh sách đã thay đổi
                } else {
                    Utils.CartListBuy.remove(cartModel);
                    EventBus.getDefault().postSticky(new totalAmountEvent());
                    cartListChanged = true; // Đánh dấu rằng danh sách đã thay đổi
                }
            }
        });

        holder.setListenner(new ImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int value) {
                if (value == 1) {
                    if (cartModelList.get(pos).getQuality() > 1) {
                        int numnew = cartModelList.get(pos).getQuality() - 1;
                        cartModelList.get(pos).setQuality(numnew);
                        //Tính tổng tiền
                        holder.item_cart_quanlity.setText(cartModelList.get(pos).getQuality() + " ");
                        long price = cartModelList.get(pos).getQuality() * cartModelList.get(pos).getPrice();
                        holder.item_cart_priceproduct.setText("Tiền: " + decimalFormat.format(price));
                        EventBus.getDefault().postSticky(new totalAmountEvent());
                    } else if (cartModelList.get(pos).getQuality() == 1) {
                        //Thông báo khi xóa sản phẩm
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm khỏi giỏ hàng không?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.CartList.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new totalAmountEvent());
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                } else if (value == 2) {
                    if (cartModelList.get(pos).getQuality() < cartModelList.get(pos).getQuantityInStock()) {
                        int numnew = cartModelList.get(pos).getQuality() + 1;
                        cartModelList.get(pos).setQuality(numnew);
                    }
                    //Tính tổng tiền
                    holder.item_cart_quanlity.setText(cartModelList.get(pos).getQuality() + " ");
                    long price = cartModelList.get(pos).getQuality() * cartModelList.get(pos).getPrice();
                    holder.item_cart_priceproduct.setText("Tiền: " + decimalFormat.format(price));
                    EventBus.getDefault().postSticky(new totalAmountEvent());
                }

            }
        });
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MyViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        // Nếu danh sách đã thay đổi, gửi một sự kiện thông báo rằng cần làm mới dữ liệu
        if (cartListChanged) {
            EventBus.getDefault().postSticky(new totalAmountEvent());
            cartListChanged = false; // Đặt lại biến đánh dấu
        }
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_cart_img, item_cart_delete, item_cart_add;
        TextView item_cart_name, item_cart_price, item_cart_quanlity, item_cart_priceproduct;
        CheckBox item_cart_check;
        ImageClickListenner listenner;

        public void setListenner(ImageClickListenner listenner) {
            this.listenner = listenner;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_cart_img = itemView.findViewById(R.id.item_cart_img);
            item_cart_name = itemView.findViewById(R.id.item_cart_name);
            item_cart_price = itemView.findViewById(R.id.item_cart_price);
            item_cart_quanlity = itemView.findViewById(R.id.item_cart_quanlity);
            item_cart_priceproduct = itemView.findViewById(R.id.item_cart_priceproduct);
            item_cart_add = itemView.findViewById(R.id.item_cart_add);
            item_cart_delete = itemView.findViewById(R.id.item_cart_delete);
            item_cart_check = itemView.findViewById(R.id.item_cart_check);
            //event click
            item_cart_add.setOnClickListener(this);
            item_cart_delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == item_cart_delete) {
                listenner.onImageClick(v, getAdapterPosition(), 1);
                //1 tru
            } else if (v == item_cart_add) {
                //2 cong
                listenner.onImageClick(v, getAdapterPosition(), 2);
            }
        }
    }
}

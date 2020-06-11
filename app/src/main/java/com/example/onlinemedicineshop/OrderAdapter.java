package com.example.onlinemedicineshop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order> {
    Context context;
    List<Order> list;
    public OrderAdapter(Context context, List<Order> list) {
        super(context, R.layout.order_list_item, list);
        this.context = context;
        this.list = list;
    }

    class ViewHolder {
        TextView t1, t2,t3,t4;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.order_list_item, parent, false);
            holder = new ViewHolder();
            holder.t1 = convertView.findViewById(R.id.tvQtn);
            holder.t2 = convertView.findViewById(R.id.tvCost);
            holder.t3 = convertView.findViewById(R.id.tvOrderDate);
            holder.t4=convertView.findViewById(R.id.tvDeliveryDate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.t1.setText("Общее кол-во: "+list.get(position).getQtn());
        holder.t2.setText("Общая стоимость: "+list.get(position).getCost()+"TK");
        holder.t3.setText("Заказ : "+list.get(position).getOrderDate());
        holder.t4.setText("Доставка : "+list.get(position).getDeliveryDate());
        return convertView;
    }

}

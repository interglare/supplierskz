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
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends ArrayAdapter<CartEntity> {

    Context context;
    List<CartEntity> list;
    public CartAdapter(Context context, List<CartEntity> list) {
        super(context, R.layout.cart_list_item, list);
        this.context = context;
        this.list = list;
    }

    class ViewHolder {
        ImageView ivImage;
        TextView t1, t2,t3;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cart_list_item, parent, false);
            holder = new ViewHolder();
            holder.ivImage = convertView.findViewById(R.id.ivImage);
            holder.t1 = convertView.findViewById(R.id.tvProName);
            holder.t3 = convertView.findViewById(R.id.tvQtn);
            holder.t2 = convertView.findViewById(R.id.tvCost);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivImage.setImageBitmap(getBitmapByEncodedString(list.get(position).getImage()));
        holder.t1.setText("Название товара: "+list.get(position).getName());
        holder.t2.setText("Кол-во: "+list.get(position).getQtn());
        holder.t3.setText("Итого: "+list.get(position).getCost());
        return convertView;
    }

    public static Bitmap getBitmapByEncodedString(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.NO_WRAP);
        InputStream input=new ByteArrayInputStream(decodedString);
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        return bitmap;
    }
}

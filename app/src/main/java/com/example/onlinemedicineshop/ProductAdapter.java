package com.example.onlinemedicineshop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<StockEntity> {
    Context context;
    List<StockEntity> list;
    public ProductAdapter(Context context, List<StockEntity> list) {
        super(context, R.layout.product_list_item, list);
        this.context = context;
        this.list = list;
    }

    class ViewHolder {
        TextView t1,t2,t3,t4;
        ImageView ivImage;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.product_list_item, parent, false);
            holder = new ViewHolder();
            holder.ivImage=convertView.findViewById(R.id.ivImage);
            holder.t1 = convertView.findViewById(R.id.tvPro);
            holder.t2 = convertView.findViewById(R.id.tvPrice1);
            holder.t3=convertView.findViewById(R.id.tvPrice2);
            holder.t4=convertView.findViewById(R.id.tvSell);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivImage.setImageBitmap(getBitmapByEncodedString(list.get(position).getImage()));
        holder.t1.setText("Название: "+list.get(position).getName());
        holder.t2.setText("Без скидки: "+list.get(position).getPrice1()+" TK");
        holder.t3.setText("Со скидкой: "+list.get(position).getPrice2()+" TK");
        holder.t4.setText("Всего продано: "+list.get(position).getSells());
        return convertView;
    }
    public static Bitmap getBitmapByEncodedString(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.NO_WRAP);
        InputStream input=new ByteArrayInputStream(decodedString);
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        return bitmap;


//        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        return decodedByte;

//        String imageDataBytes = base64String.substring(base64String.indexOf(",")+1);
//        InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
//        return BitmapFactory.decodeStream(stream);
    }


}

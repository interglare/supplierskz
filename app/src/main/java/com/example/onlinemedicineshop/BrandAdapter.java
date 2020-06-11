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

public class BrandAdapter extends ArrayAdapter<BrandEntity> {

    Context context;
    List<BrandEntity> list;

    public BrandAdapter(Context context, List<BrandEntity> list) {
        super(context, R.layout.brand_list_item, list);
        this.context = context;
        this.list = list;
    }

    class ViewHolder {
        TextView t1;
        ImageView ivImage;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.brand_list_item, parent, false);
            holder = new ViewHolder();
            holder.ivImage=convertView.findViewById(R.id.ivImage);
            holder.t1 = convertView.findViewById(R.id.tvName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivImage.setImageBitmap(getBitmapByEncodedString(list.get(position).getImage()));
        holder.t1.setText("Название: "+list.get(position).getName());
        return convertView;
    }
    public static Bitmap getBitmapByEncodedString(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.NO_WRAP);
        InputStream input=new ByteArrayInputStream(decodedString);
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        return bitmap;
    }


}

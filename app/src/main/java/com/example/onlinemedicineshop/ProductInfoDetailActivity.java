package com.example.onlinemedicineshop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ProductInfoDetailActivity extends AppCompatActivity {

    TextView tvTitle,tvDes;
    ImageView imageView;
    ProductTipsEntity healthTipsEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_info_detail);
        tvTitle=findViewById(R.id.tvTitle);
        tvDes=findViewById(R.id.tvDes);
        imageView=findViewById(R.id.ivImage);
        healthTipsEntity=(ProductTipsEntity) getIntent().getSerializableExtra("tip");
        setData();
    }

    private void setData() {
        tvTitle.setText(healthTipsEntity.getTitle());
        tvDes.setText(healthTipsEntity.getDescription());
        imageView.setImageBitmap(getBitmapByEncodedString(healthTipsEntity.getImage()));
    }

    public static Bitmap getBitmapByEncodedString(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.NO_WRAP);
        InputStream input=new ByteArrayInputStream(decodedString);
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        return bitmap;
    }
}

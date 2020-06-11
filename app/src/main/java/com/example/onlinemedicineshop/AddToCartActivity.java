package com.example.onlinemedicineshop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToCartActivity extends AppCompatActivity {

    TextView tvName,tvPrice;
    EditText etQtn;
    ImageView ivImage;
    StockEntity stockEntity;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        stockEntity=(StockEntity) getIntent().getSerializableExtra("stockEntity");
        user=(User)getIntent().getSerializableExtra("user");
        tvName=findViewById(R.id.tvName);
        tvPrice=findViewById(R.id.tvPrice);
        etQtn=findViewById(R.id.etQtn);
        ivImage=findViewById(R.id.ivImage);
        init();
    }

    private void init() {
        ivImage.setImageBitmap(getBitmapByEncodedString(stockEntity.getImage()));
        tvName.setText("Наименование товара : "+stockEntity.getName());
        tvPrice.setText("Было: "+stockEntity.getPrice1()+"KZT  Стало: "+stockEntity.getPrice2()+"KZT (за "+stockEntity.getUnit()+")");
    }

    public void addToCart(View view) {
        if(etQtn.getText().equals("")){
            return;
        }
        int qtn=Integer.parseInt(etQtn.getText().toString());
        if(qtn>stockEntity.getQtn()){
            Toast.makeText(this, "Превышен лимит кол-ва продукта", Toast.LENGTH_SHORT).show();
            return;
        }
        int stockId=stockEntity.getId();
        String name=stockEntity.getName();
        int cost=qtn*stockEntity.getPrice2();

        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.addToCart(user.getId(),stockId,name,qtn,cost);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("inserted")){
                    Toast.makeText(AddToCartActivity.this, "Добавлено в Корзину", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(message.equals("not inserted")){
                    Toast.makeText(AddToCartActivity.this, "Что то пошло не так", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(AddToCartActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Bitmap getBitmapByEncodedString(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.NO_WRAP);
        InputStream input=new ByteArrayInputStream(decodedString);
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        return bitmap;
    }
}

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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerReviewActivity extends AppCompatActivity {

    Order order;
    StockEntity stock;
    ImageView imageView;
    TextView tvCost,tvDate,tvPayment,tvDeliverySystem;
    EditText etQtn,etMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_review);
        order=(Order) getIntent().getSerializableExtra("order");
        imageView=findViewById(R.id.ivImage);
        tvCost=findViewById(R.id.tvCost);
        tvDate=findViewById(R.id.tvDate);
        etQtn=findViewById(R.id.etQtn);
        etMsg=findViewById(R.id.etMsg);
        tvPayment=findViewById(R.id.tvPayment);
        tvDeliverySystem=findViewById(R.id.tvDeliverySystem);
        initCaption();
    }

    private void initCaption() {
        tvCost.setText("Итого : " + order.getCost());
        tvDate.setText("Дата доставки : " + order.getDeliveryDate());
        tvPayment.setText("Система оплаты : " + order.getPayment());
        tvDeliverySystem.setText("Система доставки : " + order.getDeliverySystem());
        etQtn.setText(order.getQtn()+"");
    }

    public void submit(View view) {
        if(etMsg.getText().toString().trim().equals("")){
            Toast.makeText(this, "Пожалуйста, напишите что-нибудь.....!!!!!!!!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.addReview(order.getId(),etMsg.getText().toString().trim());
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("inserted")){
                    Toast.makeText(CustomerReviewActivity.this, "Ваш отзыв успешно отправлен", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(message.equals("not inserted")){
                    Toast.makeText(CustomerReviewActivity.this, "Что то пошло не так", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(CustomerReviewActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

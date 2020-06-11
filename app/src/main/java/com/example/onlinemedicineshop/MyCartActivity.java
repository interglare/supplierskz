package com.example.onlinemedicineshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCartActivity extends AppCompatActivity {

    User user;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        listView=findViewById(R.id.listView);
        user=(User) getIntent().getSerializableExtra("user");
        getAllMyCart();
    }

    private void getAllMyCart() {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<CartEntity>> call=apiInterface.getAllMyCart(user.getId());
        call.enqueue(new Callback<List<CartEntity>>() {
            @Override
            public void onResponse(Call<List<CartEntity>> call, Response<List<CartEntity>> response) {
                getStock(response.body());
            }

            @Override
            public void onFailure(Call<List<CartEntity>> call, Throwable t) {
                Toast.makeText(MyCartActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(List<CartEntity> list,List<StockEntity> stockList){
        for(CartEntity cart:list){
            for (StockEntity stock:stockList){
                if(cart.getStockId()==stock.getId()){
                    cart.setImage(stock.getImage());
                }
            }
        }
        CartAdapter adapter=new CartAdapter(this,list);
        listView.setAdapter(adapter);
    }

    private void getStock(final List<CartEntity> list) {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<StockEntity>> call=apiInterface.getStock();
        call.enqueue(new Callback<List<StockEntity>>() {
            @Override
            public void onResponse(Call<List<StockEntity>> call, Response<List<StockEntity>> response) {
                setAdapter(list,response.body());
            }

            @Override
            public void onFailure(Call<List<StockEntity>> call, Throwable t) {
            }
        });
    }
}

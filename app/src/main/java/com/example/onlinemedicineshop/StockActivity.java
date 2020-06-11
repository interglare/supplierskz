package com.example.onlinemedicineshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockActivity extends AppCompatActivity {

    Button btnTitle;
    ListView listView;
    List<StockEntity> list;
    String type;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        btnTitle=findViewById(R.id.btnTitle);
        listView=findViewById(R.id.listView);
        type=getIntent().getStringExtra("type");
        btnTitle.setText("Available "+type);
        user=(User) getIntent().getSerializableExtra("user");
        getProductsByCategory();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(StockActivity.this,OrderProductActivity.class);
                intent.putExtra("stockEntity",list.get(i));
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }
    public void getProductsByCategory(){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<StockEntity>> call=apiInterface.getProductsByCategory(type);
        call.enqueue(new Callback<List<StockEntity>>() {
            @Override
            public void onResponse(Call<List<StockEntity>> call, Response<List<StockEntity>> response) {
                list=response.body();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<StockEntity>> call, Throwable t) {
                Toast.makeText(StockActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setAdapter(){
        ProductAdapter adapter=new ProductAdapter(this,list);
        listView.setAdapter(adapter);
    }
}

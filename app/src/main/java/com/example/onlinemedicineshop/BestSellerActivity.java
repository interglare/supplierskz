package com.example.onlinemedicineshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BestSellerActivity extends AppCompatActivity {

    AutoCompleteTextView actvKey;
    List<StockEntity> list;
    String str[];
    ListView listView;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_seller);
        listView=findViewById(R.id.listView);
        actvKey=findViewById(R.id.actvKey);
        user=(User) getIntent().getSerializableExtra("user");
        getMostSellsMedicine();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(BestSellerActivity.this,AddToCartActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("stockEntity",list.get(i));
                startActivity(intent);
            }
        });
        actvKey.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchMedicine(actvKey.getText().toString().trim());
            }
        });
    }

    private void getMostSellsMedicine() {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<StockEntity>> call=apiInterface.getMostSellsMedicine();
        call.enqueue(new Callback<List<StockEntity>>() {
            @Override
            public void onResponse(Call<List<StockEntity>> call, Response<List<StockEntity>> response) {
                list=response.body();
                init();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<StockEntity>> call, Throwable t) {
                Toast.makeText(BestSellerActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchMedicine(String key){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<StockEntity>> call=apiInterface.searchMedicine(key);
        call.enqueue(new Callback<List<StockEntity>>() {
            @Override
            public void onResponse(Call<List<StockEntity>> call, Response<List<StockEntity>> response) {
                list=response.body();
                setAdapter();

            }

            @Override
            public void onFailure(Call<List<StockEntity>> call, Throwable t) {
                Toast.makeText(BestSellerActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        str=new String[list.size()];
        for(int i=0;i<list.size();i++){
            str[i]=list.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1, str);
        actvKey.setThreshold(1);
        actvKey.setAdapter(adapter);
    }

    public void setAdapter(){
        ProductAdapter adapter=new ProductAdapter(this,list);
        listView.setAdapter(adapter);
        actvKey.setText("");
    }
}

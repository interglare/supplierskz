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

public class ProductActivity extends AppCompatActivity {

    AutoCompleteTextView actvKey;
    List<StockEntity> list;
    String str[];
    ListView listView;
    User user;
    String brand="no";
    String category="no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        listView=findViewById(R.id.listView);
        actvKey=findViewById(R.id.actvKey);
        user=(User) getIntent().getSerializableExtra("user");
        brand=getIntent().getStringExtra("brand");
        category=getIntent().getStringExtra("category");
        if(brand.equals("no") && category.equals("no"))
            getAllMedicine();
        else if(!brand.equals("no"))
            getMedicineByBrand();
        else if(!category.equals("no"))
            getMedicineByCategory();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ProductActivity.this,AddToCartActivity.class);
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
                Toast.makeText(ProductActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMedicineByCategory() {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<StockEntity>> call=apiInterface.getMedicineByCategory(category);
        call.enqueue(new Callback<List<StockEntity>>() {
            @Override
            public void onResponse(Call<List<StockEntity>> call, Response<List<StockEntity>> response) {
                list=response.body();
                init();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<StockEntity>> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMedicineByBrand(){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<StockEntity>> call=apiInterface.getMedicineByBrand(brand);
        call.enqueue(new Callback<List<StockEntity>>() {
            @Override
            public void onResponse(Call<List<StockEntity>> call, Response<List<StockEntity>> response) {
                list=response.body();
                init();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<StockEntity>> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllMedicine() {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<StockEntity>> call=apiInterface.getStock();
        call.enqueue(new Callback<List<StockEntity>>() {
            @Override
            public void onResponse(Call<List<StockEntity>> call, Response<List<StockEntity>> response) {
                list=response.body();
                init();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<StockEntity>> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setAdapter(){
        ProductAdapter adapter=new ProductAdapter(this,list);
        listView.setAdapter(adapter);
        actvKey.setText("");
    }
}

package com.example.onlinemedicineshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    List<BrandEntity> list;
    ListView listView;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        listView=findViewById(R.id.listView);
        user=(User) getIntent().getSerializableExtra("user");
        getAllCategory();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(CategoryActivity.this, ProductActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("brand","no");
                intent.putExtra("category",list.get(i).getName());
                startActivity(intent);
            }
        });
    }

    private void getAllCategory() {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<BrandEntity>> call=apiInterface.getAllCategory();
        call.enqueue(new Callback<List<BrandEntity>>() {
            @Override
            public void onResponse(Call<List<BrandEntity>> call, Response<List<BrandEntity>> response) {
                list=response.body();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<BrandEntity>> call, Throwable t) {
                Toast.makeText(CategoryActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(){
        BrandAdapter adapter=new BrandAdapter(this,list);
        listView.setAdapter(adapter);
    }
}

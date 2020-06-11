package com.example.onlinemedicineshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInfo extends AppCompatActivity {

    ListView listView;
    List<ProductTipsEntity> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_info);
        listView=findViewById(R.id.listView);
        getAllMedicineInfo();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ProductInfo.this, ProductInfoDetailActivity.class);
                intent.putExtra("tip",list.get(i));
                startActivity(intent);
            }
        });
    }
    private void getAllMedicineInfo() {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<ProductTipsEntity>> call=apiInterface.getAllMedicineInfo();
        call.enqueue(new Callback<List<ProductTipsEntity>>() {
            @Override
            public void onResponse(Call<List<ProductTipsEntity>> call, Response<List<ProductTipsEntity>> response) {
                list=response.body();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<ProductTipsEntity>> call, Throwable t) {
                Toast.makeText(ProductInfo.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setAdapter(){
        TipsAdapter adapter=new TipsAdapter(this,list);
        listView.setAdapter(adapter);
    }

}

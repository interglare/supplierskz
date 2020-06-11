package com.example.onlinemedicineshop;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    User user;
    TextView tvOrderDate,tvCost;
    EditText etQtn;
    CalendarView dpDeliveryDate;
    String orderDate;
    RadioGroup rgDelSys,rgPaySys;
    LinearLayout llBkash;
    EditText etBkashNumber;
    boolean flag;
    String deliveryDate="";
    String payment="Оплата при доставке";
    int cost=0;
    int qtn=0;
    List<CartEntity> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        user=(User)getIntent().getSerializableExtra("user");
        tvOrderDate=findViewById(R.id.tvOrderDate);
        tvCost=findViewById(R.id.tvCost);
        rgDelSys=findViewById(R.id.rgDelSys);
        rgPaySys=findViewById(R.id.rgPaySys);
        etQtn=findViewById(R.id.etQtn);
        dpDeliveryDate=findViewById(R.id.dpDeliveryDate);
        etBkashNumber=findViewById(R.id.etBkashNumber);
        llBkash=findViewById(R.id.llBkash);
        dpDeliveryDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                deliveryDate=day+"/"+(month+1)+"/"+year+"";
            }
        });
        rgPaySys.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.getCheckedRadioButtonId()==R.id.rbCD){
                    llBkash.setVisibility(View.GONE);
                    payment="Оплата при доставке";
                }
                else if(radioGroup.getCheckedRadioButtonId()==R.id.rbBK){
                    llBkash.setVisibility(View.VISIBLE);
                    payment="Оплата сейчас";
                }
            }
        });
        flag=true;
        getAllMyCart();
    }

    private void getAllMyCart() {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<CartEntity>> call=apiInterface.getAllMyCart(user.getId());
        call.enqueue(new Callback<List<CartEntity>>() {
            @Override
            public void onResponse(Call<List<CartEntity>> call, Response<List<CartEntity>> response) {
                cartList=response.body();
                if(cartList.size()==0){
                    Toast.makeText(CheckoutActivity.this, "Корзина пуста..!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    initialize(response.body());
            }

            @Override
            public void onFailure(Call<List<CartEntity>> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initialize(List<CartEntity> list){
        for(CartEntity cart:list){
            cost+=cart.getCost();
            qtn+=cart.getQtn();
        }
        orderDate=dateCalculation();
        tvOrderDate.setText("Дата заказа: "+orderDate);
        tvCost.setText("Итого: "+cost+" KZT");
        etQtn.setText("Кол-во: "+qtn);
    }

    public String dateCalculation()
    {
        Calendar cal;
        cal = Calendar.getInstance();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat();
        return sdf.format(cal.getTime());
    }

    public void order(View view) {
        if(!flag){
            Toast.makeText(this, "Ваш заказ уже выполнен", Toast.LENGTH_SHORT).show();
            finish();
        }
        if(rgDelSys.getCheckedRadioButtonId()==-1){
            return;
        }
        if(deliveryDate.equals("")) {
            Toast.makeText(this, "Пожалуйста, выберите дату доставки..!!", Toast.LENGTH_SHORT).show();
            return;
        }
        String deliverySystem=rgDelSys.getCheckedRadioButtonId()==R.id.rbLD?"Локальная доставка":"Забрать самому";
        String status="Pending";
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.order(user.getId(),qtn,cost,orderDate,deliveryDate,deliverySystem,payment,status);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("inserted")){
                    Toast.makeText(CheckoutActivity.this, "Заказ выполнен", Toast.LENGTH_SHORT).show();
                    flag=false;
                    updateStock();
                    deleteCartItems();
                    finish();
                }
                else if(message.equals("not inserted")){
                    Toast.makeText(CheckoutActivity.this, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateStock(){
        for(CartEntity cart:cartList){
            ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
            Call<ServerResponse> call=apiInterface.updateStock(cart.getStockId(),qtn);
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Toast.makeText(CheckoutActivity.this, "Ошибка подключения", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void deleteCartItems(){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.deleteCartItems(user.getId());
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, "Пожалуйста, проверьте соединение", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

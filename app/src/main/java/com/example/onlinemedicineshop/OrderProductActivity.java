package com.example.onlinemedicineshop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderProductActivity extends AppCompatActivity {

    StockEntity product;
    User user;
    TextView tvName,tvPrice,tvUnit,tvOrderDate;
    EditText etQtn;
    CalendarView dpDeliveryDate;
    String orderDate;
    RadioGroup rgDelSys,rgPaySys;
    LinearLayout llBkash;
    EditText etBkashNumber;
    ImageView ivImage;
    boolean flag;
    String deliveryDate="";
    String payment="Оплата при доставке";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product);
        product=(StockEntity) getIntent().getSerializableExtra("stockEntity");
        user=(User)getIntent().getSerializableExtra("user");
        tvName=findViewById(R.id.tvName);
        tvPrice=findViewById(R.id.tvPrice);
        tvUnit=findViewById(R.id.tvUnit);
        tvOrderDate=findViewById(R.id.tvOrderDate);
        rgDelSys=findViewById(R.id.rgDelSys);
        rgPaySys=findViewById(R.id.rgPaySys);
        etQtn=findViewById(R.id.etQtn);
        dpDeliveryDate=findViewById(R.id.dpDeliveryDate);
//        rbLD=findViewById(R.id.rbLD);
//        rbLP=findViewById(R.id.rbLP);
//        rbBkash=findViewById(R.id.rbBkash);
//        rbCOD=findViewById(R.id.rbCOD);
        etBkashNumber=findViewById(R.id.etBkashNumber);
//        etBkashPin=findViewById(R.id.etBkashPin);
        ivImage=findViewById(R.id.ivImage);
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
                    payment="Оплатить сейчас";
                }
            }
        });
        flag=true;
        initialize();
    }

    public void initialize(){
        ivImage.setImageBitmap(getBitmapByEncodedString(product.getImage()));
        tvName.setText("Название: "+product.getName());
        tvPrice.setText("Было: "+product.getPrice1()+"KZT  Со скидкой: "+product.getPrice2()+"KZT");
        orderDate=dateCalculation();
        tvOrderDate.setText("Дата заказа: "+orderDate);
        tvUnit.setText(product.getUnit());
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
        if(etQtn.getText().equals("") || rgDelSys.getCheckedRadioButtonId()==-1){
            return;
        }
        int qtn=Integer.parseInt(etQtn.getText().toString());
        if(qtn>product.getQtn()){
            Toast.makeText(this, "Превышен лимит количества продукта", Toast.LENGTH_SHORT).show();
            return;
        }
        int productId=product.getId();
        String productName=product.getName();
        int cost=qtn*product.getPrice2();
        if(deliveryDate.equals("")) {
            Toast.makeText(this, "Пожалуйста, выберите дату доставки..!!", Toast.LENGTH_SHORT).show();
            return;
        }
        String deliverySystem=rgDelSys.getCheckedRadioButtonId()==R.id.rbLD?"Локальная доставка":"Забрать самому";

        String status="Pending";
//        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
//        Call<ServerResponse> call=apiInterface.order(user.getId(),productId,productName,qtn,cost,orderDate,deliveryDate,deliverySystem,payment,status);
//        call.enqueue(new Callback<ServerResponse>() {
//            @Override
//            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                ServerResponse serverResponse=response.body();
//                String message=serverResponse.getResponse();
//                if(message.equals("inserted")){
//                    Toast.makeText(OrderProductActivity.this, "Your Order is Successful", Toast.LENGTH_SHORT).show();
//                    flag=false;
//                    finish();
//                }
//                else if(message.equals("not inserted")){
//                    Toast.makeText(OrderProductActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                Toast.makeText(OrderProductActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public static Bitmap getBitmapByEncodedString(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.NO_WRAP);
        InputStream input=new ByteArrayInputStream(decodedString);
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        return bitmap;
    }
}

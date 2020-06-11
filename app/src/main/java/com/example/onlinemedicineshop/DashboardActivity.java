package com.example.onlinemedicineshop;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    List<User> list;
    User user;
    SharedPreferences sharedPreferences;
    private NotificationManager notifManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getUser();
    }

    public void getUser(){
        String email=getIntent().getStringExtra("email");
        String password=getIntent().getStringExtra("password");
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<User>> call=apiInterface.getUser(email,password);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                list=response.body();
                user=list.get(0);
                isSeenOrder(user);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void isSeenOrder(final User user){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.isSeenOrder(user.getId());
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                if(serverResponse.getResponse().equals("exist")){
                    Toast.makeText(DashboardActivity.this, "111111111", Toast.LENGTH_SHORT).show();
                    createNotification(user,"Спасибо за заказ !!! Ваш заказ в пути!!!!",DashboardActivity.this);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNotification(){
        String msg="Спасибо за заказ ... !!! Ваш заказ в пути....!!!!";
        Toast.makeText(this, ""+msg, Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder builder=new NotificationCompat.Builder(DashboardActivity.this)
                .setSmallIcon(R.drawable.d)
                .setContentTitle("Поставщики")
                .setContentText(msg)
                .setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(DashboardActivity.this);
        notificationManagerCompat.notify(1,builder.build());
//        Intent intent=new Intent(DashboardActivity.this,TransactionActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent=PendingIntent.getActivity(DashboardActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0,builder.build());
    }

    public void logoutClick(View view) {
        sharedPreferences=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.apply();
        finish();
    }

    public void profileClick(View view) {
        Intent intent=new Intent(DashboardActivity.this,ProfileActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void transactionClick(View view) {
        Intent intent=new Intent(DashboardActivity.this,TransactionActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void pendingOrderClick(View view) {
        Intent intent=new Intent(DashboardActivity.this,PendingOrderActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void medicineClick(View view) {
        Intent intent=new Intent(DashboardActivity.this, ProductActivity.class);
        intent.putExtra("user",user);
        intent.putExtra("brand","no");
        intent.putExtra("category","no");
        startActivity(intent);
    }

    public void bestSellerClick(View view) {
        Intent intent=new Intent(DashboardActivity.this,BestSellerActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void categoryClick(View view) {
        Intent intent=new Intent(DashboardActivity.this,CategoryActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void brandClick(View view) {
        Intent intent=new Intent(DashboardActivity.this,BrandActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void tipsClick(View view) {
        Intent intent=new Intent(DashboardActivity.this, ProductTipsActivity.class);
        startActivity(intent);
    }

    public void medicineInfoClick(View view) {
        Intent intent=new Intent(DashboardActivity.this, ProductInfo.class);
        startActivity(intent);
    }

    public void checkoutClick(View view) {
        Intent intent=new Intent(DashboardActivity.this,CheckoutActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    public void myCartClick(View view) {
        Intent intent=new Intent(DashboardActivity.this,MyCartActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }

//    public void clinicalClick(View view) {
//        Intent intent=new Intent(DashboardActivity.this,StockActivity.class);
//        intent.putExtra("user",user);
//        intent.putExtra("type","Clinical Instrument");
//        startActivity(intent);
//    }
//
//    public void generalClick(View view) {
//        Intent intent=new Intent(DashboardActivity.this,StockActivity.class);
//        intent.putExtra("user",user);
//        intent.putExtra("type","General Medicine");
//        startActivity(intent);
//    }

//    public void foreignClick(View view) {
//        Intent intent=new Intent(DashboardActivity.this,StockActivity.class);
//        intent.putExtra("user",user);
//        intent.putExtra("type","Foreign Medicine");
//        startActivity(intent);
//    }
//
//    public void surgicalClick(View view) {
//        Intent intent=new Intent(DashboardActivity.this,StockActivity.class);
//        intent.putExtra("user",user);
//        intent.putExtra("type","Surgical Instrument");
//        startActivity(intent);
//    }

    public void createNotification(User user,String aMessage, Context context) {
        final int NOTIFY_ID = 0; // ID of notification
        String id = "default_channel_id";
        String title = "Default Channel";
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        if (notifManager == null) {
            notifManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, TransactionActivity.class);
            intent.putExtra("user",user);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
//                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
//                    .setTicker(aMessage)
                    .setContentTitle("Health Care App")
                    .setContentText(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        }
        else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, TransactionActivity.class);
            intent.putExtra("user",user);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder)   // required
//                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
//                    .setTicker(aMessage)
                    .setContentTitle("Health Care App")
                    .setContentText(aMessage)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
        setSeenOrder(user);
    }

    private void setSeenOrder(User user){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.setSeenOrder(user.getId());
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Ошибка подключения", Toast.LENGTH_LONG).show();
            }
        });
    }
}

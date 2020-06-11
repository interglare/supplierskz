package com.example.onlinemedicineshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView tvRegister;
    EditText etEmail,etPassword;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvRegister=findViewById(R.id.tvRegister);
        etEmail=findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPassword);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("email") && sharedPreferences.contains("password")) {
            String email=sharedPreferences.getString("email", "");
            String password=sharedPreferences.getString("password", "");
            etEmail.setText(email);
            etPassword.setText(password);
        }

    }

    public void loginClick(View view) {
        final String email=etEmail.getText().toString();
        final String password=etPassword.getText().toString();
        if(email.equals("") || password.equals("")){
            Toast.makeText(this, "Пожалуйста, заполните все ... !!!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.isUser(email,password);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                if(serverResponse.getResponse().equals("exist")){
                    Toast.makeText(LoginActivity.this, "Успешный вход",
                            Toast.LENGTH_SHORT).show();
                    setLoginData(email,password);
                    Intent intent=new Intent(LoginActivity.this,DashboardActivity.class);
                    intent.putExtra("email",email);
                    intent.putExtra("password",password);
                    startActivity(intent);
                }
                else if(serverResponse.getResponse().equals("not exist")){
                    Toast.makeText(LoginActivity.this, "Ошибка входа", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setLoginData(String email,String password){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("email",email);
        editor.putString("password",password);
        editor.commit();
        etEmail.setText("");
        etPassword.setText("");
    }
}

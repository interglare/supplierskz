package com.example.onlinemedicineshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etName,etEmail,etPassword,etContact,etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etContact=findViewById(R.id.etContact);
        etAddress=findViewById(R.id.etAddress);
    }

    public void registerClick(View view) {
        if(etName.getText().toString().equals("") || etEmail.getText().toString().equals("") || etPassword.getText().toString().equals("") ||
                etContact.getText().toString().equals("") || etAddress.getText().toString().equals("")) {
            Toast.makeText(this, "Пожалуйста, заполните все...!!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
            return;
        }
//        if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches() || !Patterns.PHONE.matcher(etContact.getText().toString()).matches()){
//            Toast.makeText(this, "Please Enter valid Information...!!!", Toast.LENGTH_SHORT).show();
//            return;
//        }
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.registration(etName.getText().toString(),etEmail.getText().toString()
                ,etPassword.getText().toString(),etContact.getText().toString(),etAddress.getText().toString());
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("exist")){
                    Toast.makeText(RegisterActivity.this, "Этот пользователь уже существует", Toast.LENGTH_SHORT).show();
                }
                else if(message.equals("inserted")){
                    Toast.makeText(RegisterActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(message.equals("not inserted")){
                    Toast.makeText(RegisterActivity.this, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

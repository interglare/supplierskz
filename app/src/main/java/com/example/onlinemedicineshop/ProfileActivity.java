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

public class ProfileActivity extends AppCompatActivity {

    EditText etId,etName,etEmail,etPassword,etContact,etAddress;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        etId=findViewById(R.id.etId);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etContact=findViewById(R.id.etContact);
        etAddress=findViewById(R.id.etAddress);
        user=(User)getIntent().getSerializableExtra("user");
        initialize();
    }

    public void initialize(){
        etId.setText("User Id : "+user.getId());
        etName.setText(user.getName());
        etEmail.setText(user.getEmail());
        etPassword.setText(user.getPassword());
        etContact.setText(user.getContact());
        etAddress.setText(user.getAddress());
    }

    public void saveClick(View view) {
        String name=etName.getText().toString();
        String email=etEmail.getText().toString();
        String password=etPassword.getText().toString();
        String contact=etContact.getText().toString();
        String address=etAddress.getText().toString();
        if(name.equals("") || email.equals("") || password.equals("") || contact.equals("") || address.equals("")){
            Toast.makeText(this, "Пожалуйста, убедитесь, что ваша информация", Toast.LENGTH_LONG).show();
            return;
        }
//        if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches() || !Patterns.PHONE.matcher(etContact.getText().toString()).matches()){
//            Toast.makeText(this, "Please Enter valid Information...!!!", Toast.LENGTH_SHORT).show();
//            return;
//        }
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.editProfile(user.getId(),etName.getText().toString(),
                etEmail.getText().toString(),etPassword.getText().toString(),etContact.getText().toString(),etAddress.getText().toString());
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("update")){
                    Toast.makeText(ProfileActivity.this, "Ваш профиль обновлен", Toast.LENGTH_LONG).show();
                }
                else if(message.equals("not update")){
                    Toast.makeText(ProfileActivity.this, "Что-то пошло не так", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Ошибка подключения", Toast.LENGTH_LONG).show();
            }
        });
    }
}

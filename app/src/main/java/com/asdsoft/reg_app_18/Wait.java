package com.asdsoft.reg_app_18;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Wait extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        recheck();
    }

    public void refersh(View view) {
        recheck();
    }
    void recheck(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        ApiClient api = retrofit.create(ApiClient.class);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Call<List<DataRecv>> call = api.checkData(firebaseUser.getPhoneNumber());
        call.enqueue(new Callback<List<DataRecv>>() {
            @Override
            public void onResponse(Call<List<DataRecv>> call, Response<List<DataRecv>> response) {
                List<DataRecv> out = response.body();
                DataRecv d = out.get(0);
                Log.e("TAG", String.valueOf(d.RES));
                if(d.RES == 200){
                    startA(d.gname);
                }
                else {
                    Toast.makeText(Wait.this,"NOT ADDED" ,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<DataRecv>> call, Throwable t) {
                Toast.makeText(Wait.this,"CHECK YOUR INTERNET" + t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void  startA(String gname){
        Intent i = new Intent(Wait.this,MainActivity.class);
        i.putExtra("gname",gname);
        startActivity(i);
    }

}

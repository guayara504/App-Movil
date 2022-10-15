package com.pm.login_20;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.pm.login_20.Interfaces.ProductoAPI;
import com.pm.login_20.Model.LogPersona;
import com.pm.login_20.Model.Logueado;
import com.pm.login_20.Utils.Constants;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "Bienvenidos a Sci High";
    ProductoAPI productoAPI;
    List<LogPersona> logPersonas;
    private String emailAdd;

    private static final String TAG = "MainActivity";
    private static final String TAG2 = "Logueado";

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button btnLog = findViewById(R.id.btn_login);

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);

        //

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String varCorreo= email.getText().toString();

                //getOne(varCorreo);
                getAll();
            }
        });
    }

    /*public void GET_UserById(View view) {
        Call<List<LogPersona>> loginCall = apiLogin.getLogin();
        loginCall.enqueue(new Callback<List<LogPersona>>() {
            @Override
            public void onResponse(Call<List<LogPersona>> call, Response<List<LogPersona>> response) {
                Log.e(TAG, "on response: " + response.body());
            }

            @Override
            public void onFailure(Call<List<LogPersona>> call, Throwable t) {
                Log.e(TAG,"on failure: "+t.getLocalizedMessage());
            }
        });
    }*/

    private void getOne(String email){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        productoAPI = retrofit.create(ProductoAPI.class);
        Call<List<LogPersona>>call = productoAPI.getOne(email);
        call.enqueue(new Callback<List<LogPersona>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<LogPersona>> call, Response<List<LogPersona>> response) {
                if(!response.isSuccessful()){
                    Toast toast=Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_LONG);
                    toast.show();

                    Log.e("Response err: ",response.message());
                    return;
                }
                logPersonas=response.body();

                Log.i("prueba", String.valueOf(logPersonas));

            }

            @Override
            public void onFailure(Call<List<LogPersona>> call, Throwable t) {
                Toast toast=Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG);
                toast.show();
                Log.e("Throw err: ",t.getMessage());
            }
        });
    }


    private void getAll(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        productoAPI = retrofit.create(ProductoAPI.class);
        Call<List<LogPersona>>call = productoAPI.getAll();
        call.enqueue(new Callback<List<LogPersona>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<LogPersona>> call, Response<List<LogPersona>> response) {
                if(!response.isSuccessful()){
                    Toast toast=Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_LONG);
                    toast.show();

                    Log.e("Response err: ",response.message());
                    return;
                }
                logPersonas=response.body();
                //final String[] EmailAddress = new String[1];
                logPersonas.forEach(p -> {
                    int i = Log.i("Prods: ", p.getEmail().toString());
                    emailAdd=p.getEmail();
                });
                Intent intent = new Intent(MainActivity.this, Logueado.class);
                startActivity(intent);

                Log.i("prueba",emailAdd);

            }

            @Override
            public void onFailure(Call<List<LogPersona>> call, Throwable t) {
                Toast toast=Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG);
                toast.show();
                Log.e("Throw err: ",t.getMessage());
            }
        });
    }


    /*public void GET_UserById(View view) {
        Call<Login> loginCall = apiLogin.getLoginOne(3);
        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Log.e(TAG, "on response: "+response.body());
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.e(TAG,"on failure: "+t.getLocalizedMessage());
            }
        });
    }*/
}
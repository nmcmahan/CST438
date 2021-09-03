package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private APIGet apiGet;
    int loginid = 0;

    List<String> pwdList = Arrays.asList("test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9", "test10");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Log.i("List test:", pwdList.get(0));
        //List<Users> userlist;

        setContentView(R.layout.activity_main);
        EditText username = findViewById(R.id.editTextTextPersonName);
        EditText password = findViewById(R.id.editTextTextPassword);
        Button button = findViewById(R.id.button);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiGet = retrofit.create(APIGet.class);
        //Call<List<Users>> call = apiGet.getUsers();


        button.setOnClickListener(view -> {
            String uname = username.getText().toString();
            String pwd = password.getText().toString();
            getUser(uname, pwd);
        });

    }
    private void getUser(String name, String pass) {
        Call<List<Users>> call = apiGet.getUsers();
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                EditText username = findViewById(R.id.editTextTextPersonName);
                EditText password = findViewById(R.id.editTextTextPassword);
                boolean namefound=false;
                if (!response.isSuccessful()) {
                    username.setError("Something went wrong");
                    return;
                }
                List<Users> users = response.body();
                for (Users user : users) {
                    String uname = user.getUsername();
                    int uid = user.getUserId();
                    if (uname.equals(name) && pwdList.get(uid-1).equals(pass)) {
                        loginid = uid;
                        Intent intent = new Intent(MainActivity.this, PostActivity.class);
                        intent.putExtra("USERNAME", name);
                        intent.putExtra("USERID", uid);
                        startActivity(intent);
                        return;
                    } else if (uname.equals(name)) {
                        namefound=true;
                    }
                }
                if (namefound) {
                    password.setError("Incorrect password");
                } else {
                    username.setError("Username not found, try again");
                }
            }
            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                Log.i("Failure Message", "Throwable: ", t);
            }
        });
    }
}
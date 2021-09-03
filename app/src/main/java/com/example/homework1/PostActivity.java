package com.example.homework1;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts);
        TextView textView = findViewById(R.id.textView2);
        ScrollView scrollView = findViewById(R.id.scrollView2);
        TextView posts = findViewById(R.id.textView3);

        String username = getIntent().getStringExtra("USERNAME");
        int userid = getIntent().getIntExtra("USERID", 0);
        String welcome = "Welcome " + username + " user id " + userid + "\n\n";
        posts.append(welcome);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIGet apiGet = retrofit.create(APIGet.class);
        Call<List<Post>> call = apiGet.getPosts(userid);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    posts.setText("Code: " + response.code());
                    return;
                }
                List<Post> postlist = response.body();
                for (Post post : postlist) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";
                    posts.append(content);
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                posts.setText(t.getMessage());
            }
        });
    }
    }


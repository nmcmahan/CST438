package com.example.homework1;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIGet {
    @GET("posts")
    Call<List<Post>> getPosts(@Query("userId") int userid);

    @GET("users")
    Call<List<Users>> getUsers();
}

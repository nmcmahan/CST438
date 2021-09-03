package com.example.homework1;

import com.google.gson.annotations.SerializedName;

public class Users {
        @SerializedName("id")
        private int userId;

        private String username;

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}

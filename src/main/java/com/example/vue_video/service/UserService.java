package com.example.vue_video.service;

import com.example.vue_video.pojo.User;

public interface UserService {
    User findByUserName(String username);

    void register(String username, String password,String email);
}

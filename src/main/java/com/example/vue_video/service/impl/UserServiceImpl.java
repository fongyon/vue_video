package com.example.vue_video.service.impl;

import com.example.vue_video.mapper.UserMapper;
import com.example.vue_video.pojo.User;
import com.example.vue_video.service.UserService;
import com.example.vue_video.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        User user = userMapper.findByUserName(username);
        return user;
    }

    @Override
    public void register(String username, String password,String email) {
        User user = new User();
        //加密
        String salt= MD5Util.createSlat(8);
        String psw = MD5Util.MD5Encode(password, salt);
        user.setSalt(salt);
        //添加
        userMapper.adduser(username,psw,salt,email);
    }
}

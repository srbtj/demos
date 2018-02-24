package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
    // 根据用户名查找用户信息
    public User queryByName(String name);
}

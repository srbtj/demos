package com.example.demo.mapper;

import com.example.demo.model.User;

public interface UserMapper {

    public User queryByName(String name);
}

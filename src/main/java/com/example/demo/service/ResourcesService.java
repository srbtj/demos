package com.example.demo.service;

import com.example.demo.model.Resources;

import java.util.List;
import java.util.Map;

public interface ResourcesService {

    // 查找所有资源数据
    public List<Resources> queryAll();
    // 根据用户查找对应的资源
    public List<Resources> loadUserResources(Map<String, Object> map);
}

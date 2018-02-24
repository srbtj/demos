package com.example.demo.mapper;

import com.example.demo.model.Resources;

import java.util.List;
import java.util.Map;

public interface ResourcesMapper {

    public List<Resources> queryAll();
    public List<Resources> loadUserResources(Map<String, Object> map);
}

package com.example.demo.service.impl;

import com.example.demo.mapper.ResourcesMapper;
import com.example.demo.model.Resources;
import com.example.demo.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("resourcesService")
public class ResourcesServiceImpl implements ResourcesService{

    @Autowired
    private ResourcesMapper resourcesMapper;
    @Override
    public List<Resources> queryAll() {
        return resourcesMapper.queryAll();
    }

    @Override
    public List<Resources> loadUserResources(Map<String, Object> map) {
        return resourcesMapper.loadUserResources(map);
    }
}

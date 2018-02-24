package com.example.demo.shiro;

import com.example.demo.model.Resources;
import com.example.demo.service.ResourcesService;
import com.github.pagehelper.util.StringUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShiroService {

    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Autowired
    private ResourcesService resourcesService;

    /**
     *  重新初始化权限
     */
    public Map<String, String> loadFilterChainDefinitions() {

        Logger.getLogger(getClass()).info("======================: 初始化权限");
        Map<String, String> filterMap = new LinkedHashMap<String, String>();
        filterMap.put("/logout", "logout");
        filterMap.put("/css/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/img/**", "anon");
        filterMap.put("/font-awesome/**", "anon");
        List<Resources> resourcesList = resourcesService.queryAll();
//        System.out.print(resourcesList.size());

        for(Resources res: resourcesList) {
            if(StringUtil.isNotEmpty(res.getResurl())) {
                String permission = "perms[" + res.getResurl() + "]";
                filterMap.put(res.getResurl(), permission);
            }
        }

        return filterMap;
    }

    /**
     *  重新更新权限
     */
    public void updatePermission() {
        synchronized (shiroFilterFactoryBean) {
            AbstractShiroFilter shiroFilter = null;

            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                throw new RuntimeException("get shiroFilter from shiroFilterFactoryBean error!");
            }

            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver)shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager chainManager = (DefaultFilterChainManager)filterChainResolver.getFilterChainManager();

            // 清空老权限控制
            chainManager.getFilterChains().clear();

            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChainDefinitions());

            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();

            for (Map.Entry<String, String> entry: chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                chainManager.createChain(url, chainDefinition);
            }

            Logger.getLogger(getClass()).info("======================: 权限更新成功");
        }
    }
}

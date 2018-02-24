package com.example.demo.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.example.demo.model.Resources;
import com.example.demo.service.ResourcesService;
import com.example.demo.shiro.MyShiroRealm;
import com.github.pagehelper.util.StringUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Autowired(required = false)
    private ResourcesService resourcesService;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     *  为了在 thymeleaf 中中使用 shiro 标签
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public SecurityManager securityManager() {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置 realm
        securityManager.setRealm(myShiroRealm());
        // 自定义缓存
        securityManager.setCacheManager(cacheManager());
        // 自定义session
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        Logger.getLogger(getClass()).info("======================: ShiroConfig.shiroFilter");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // ShiroFilterFactoryBean 必须设置 SecurityManager 否则启动报错
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置登录页面 默认加载 Web 工程目录下的 /login.jsp
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 设置登录成功后跳转的页面
        shiroFilterFactoryBean.setSuccessUrl("/usersPage");
        // 设置未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        // 设置拦截器
        Map<String, String> filterMap = new LinkedHashMap<String, String>();
        // 配置退出过滤器  具体的退出 shiro 内部实现
        filterMap.put("/logout", "logout");
        filterMap.put("/css/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/img/**", "anon");
        filterMap.put("/font-awesome/**", "anon");
        // 过滤器 由上向下执行 一般将 /** 放在最后边
        // authc : 所有的url都必须认证通过后 才可访问   anon: 所有的url都可匿名访问
        // 自定义加载权限资源关系
        // todo 查找资源表
        List<Resources> resourcesList = resourcesService.queryAll();
        System.out.print(resourcesList.size());
        for (Resources res: resourcesList) {

            if (StringUtil.isNotEmpty(res.getResurl())) {
                String permission = "perms[" + res.getResurl() + "]";
                filterMap.put(res.getResurl(), permission);
            }
        }
        filterMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     *  凭证匹配器
     *   密码校验 由 shiro的 SimpleAuthenticationInfo进行处理
     *    所以需要修改 doGetAuthenticationInfo中的代码
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5"); // 散列算法  MD5
        matcher.setHashIterations(2); // 散列的次数  相当于 md5(md5(""));
        return matcher;
    }

    /**
     *  开启 Shiro aop 注解支持
     *   使用代理方式 需要开启代码支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     *
     * @return
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        Logger.getLogger(getClass()).info("\"======================: MyShiroRealm 初始化\"");
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     *  配置 shiro redisManager
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        Logger.getLogger(getClass()).info("======================: RedisManager 初始化");
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        redisManager.setExpire(1800); // 设置缓存过期时间
        return redisManager;
    }

    /**
     *  CacheManager 缓存 redis 实现
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager() {
        Logger.getLogger(getClass()).info("======================: cacheManager");
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        return cacheManager;
    }

    /**
     *  RedisSessionDao shiro sessionDao层实现 通过 redis
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        return sessionDAO;
    }

    /**
     *  shiro session管理
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        Logger.getLogger(getClass()).info("======================: sessionManager");
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionDAO(redisSessionDAO());
        return defaultWebSessionManager;
    }
}

package com.example.demo.shiro;

import com.example.demo.model.Resources;
import com.example.demo.model.User;
import com.example.demo.service.ResourcesService;
import com.example.demo.service.UserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private RedisSessionDAO redisSessionDAO;

    /**
     *  授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) SecurityUtils.getSubject().getPrincipal(); // 获取当前登录的用户信息
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userid", user.getId());
        // 根据当前用户加载对应的资源
        List<Resources> resourcesList = resourcesService.loadUserResources(map);
        Logger.getLogger(getClass()).info("======================: 授权数 " + resourcesList.size());
        // 权限信息对象  用来存放查出的用户的所有角色 及 权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for(Resources res: resourcesList) {
            info.addStringPermission(res.getResurl());
        }
        return info;
    }

    /**
     *  认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String)token.getPrincipal();
        User user = userService.queryByName(username);

        if (null == user) throw new UnknownAccountException();
        if (0 == user.getEnable()) throw new LockedAccountException(); // 账号锁定
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                user,  // 用户
                user.getPassword(), // 用户密码
                ByteSource.Util.bytes(username),
                getName() // realm name
        );

        // 验证通过后 将用户信息存放至session中
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("userSession", user);
        session.setAttribute("userSessionId", user.getId());
        return info;
    }

    /**
     *  根据 userId 清除当前session存在的用户权限数据
     * @param userIds
     */
    public void clearUserAuthByUserId(List<Integer> userIds) {

        if (null == userIds || userIds.size() == 0) return;

        // 获取所有 session
        Collection<Session> sessions= redisSessionDAO.getActiveSessions();
        // 定义返回
        List<SimplePrincipalCollection> list = new ArrayList<SimplePrincipalCollection>();

        for (Session session: sessions) {
            // 获取 Session 登录信息
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if ( null != obj && obj instanceof SimplePrincipalCollection) {
                SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
                obj = spc.getPrimaryPrincipal();

                if (null != obj && obj instanceof User) {
                    User user = (User) obj;
                    Logger.getLogger(getClass()).info("======================: 消除当前用户 " + user.toString());
                    if (null != user && userIds.contains(user.getId())) {
                        list.add(spc);
                    }
                }
            }
        }

        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();

        MyShiroRealm realm = (MyShiroRealm) securityManager.getRealms().iterator().next();

        for (SimplePrincipalCollection simplePrincipalCollection: list) {
            realm.clearCachedAuthorizationInfo(simplePrincipalCollection);
        }
    }
}

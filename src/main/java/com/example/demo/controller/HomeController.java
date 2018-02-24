package com.example.demo.controller;

import com.example.demo.model.User;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    /**
     *  指定加载登录页
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     *  用户登录
     * @param request
     * @param user 用户登录信息
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, User user, Model model) {
        Logger.getLogger(getClass()).info("======================: 用户登录");
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            model.addAttribute("msg", "用户名或密码不能为空!");
            return "login";
        }

        // 验证用户信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            // 登录成功 重定向至成功页面
            subject.login(token);
            return "redirect:usersPage";
        } catch (LockedAccountException e) { // 账号锁定异常
            token.clear();
            model.addAttribute("msg", "用户已被锁定不能登录, 请与管理员联系!");
            return "login";
        } catch (AuthenticationException e) {
            token.clear();
            model.addAttribute("msg", "用户名或密码不正确!");
            return "login";
        } catch (Exception e) {
            token.clear();
            model.addAttribute("msg", "未知异常!");
            return "login";
        }
    }

    /**
     *  登录成功后 跳转至该页面
     * @return
     */
    @RequestMapping(value = "/usersPage")
    public String usersPage() {
        return "/user/users";
    }

    /**
     * 角色页
     * @return
     */
    @RequestMapping("/rolesPage")
    public String rolesPage() {
        return "role/roles";
    }

    /**
     *  资源页
     * @return
     */
    @RequestMapping("/resourcesPage")
    public String resourcesPage() {
        return "resource/resources";
    }
    /**
     *  未授权页面
     * @return
     */
    @RequestMapping("/403")
    public String forbidden() {
        return "403";
    }
}

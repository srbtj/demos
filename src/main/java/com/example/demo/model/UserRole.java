package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "user_role")
public class UserRole {

    private static final long serialVersionUID = -916411139749530670L;
    @Column(name = "userId")
    private Integer userid;
    @Column(name = "roleId")
    private Integer roleid;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }
}

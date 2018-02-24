package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "role_resources")
public class RoleResourcesKey implements Serializable{

    private static final long serialVersionUID = -8559867942708057891L;

    @Id
    @Column(name = "roleId")
    private Integer roleid;
    @Id
    @Column(name = "resourcesId")
    private Integer resourcesid;

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getResourcesid() {
        return resourcesid;
    }

    public void setResourcesid(Integer resourcesid) {
        this.resourcesid = resourcesid;
    }
}

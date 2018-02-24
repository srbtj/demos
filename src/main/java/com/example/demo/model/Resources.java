package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;

public class Resources implements Serializable{
    private static final long serialVersionUID = -6812242071705361506L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "restUrl")
    private String resurl;
    private Integer type;
    @Column(name = "parentId")
    private Integer parentid;
    private Integer sort;

    @Transient
    private String checked;

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResurl() {
        return resurl;
    }

    public void setResurl(String resurl) {
        this.resurl = resurl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Resources{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", resurl='" + resurl + '\'' +
                ", type=" + type +
                ", parentid=" + parentid +
                ", sort=" + sort +
                '}';
    }
}

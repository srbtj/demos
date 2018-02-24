package com.example.demo.model;

import javax.persistence.*;
import java.io.Serializable;

public class Role implements Serializable{

    private static final long serialVersionUID = -6140090613812307452L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "roleDesc")
    private String roledesc;

    @Transient
    private Integer selected;

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoledesc() {
        return roledesc;
    }

    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roledesc='" + roledesc + '\'' +
                '}';
    }
}

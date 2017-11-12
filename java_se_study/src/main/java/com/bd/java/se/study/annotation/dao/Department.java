package com.bd.java.se.study.annotation.dao;

@Table("department")
public class Department {
    @Column("id")
    private int id;
    @Column("dep_name")
    private String depName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }
}

package com.wenna4.ppp.Intf.Entity;

import java.util.Date;


public class UserTime {
    private int id;
    private int userId;
    private String name;
    private Date addTime;

    @Override
    public String toString() {
        return "UserTime{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", addTime=" + addTime +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
